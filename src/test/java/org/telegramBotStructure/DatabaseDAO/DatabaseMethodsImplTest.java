package org.telegramBotStructure.DatabaseDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.telegramBotStructure.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(locations = "classpath:applicationContext.xml")
@Transactional
public class DatabaseMethodsImplTest {

    @Autowired
    private DatabaseMethods databaseMethods;

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;


    @BeforeEach
    public void setUp() {
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void testSetAndGetAdmin() {
        // Создаем админа
        Admin admin = new Admin(1234569L, true);
        databaseMethods.setAdmin(admin);

        // Проверяем, что админ был корректно добавлен
        Admin retrievedAdmin = databaseMethods.getAdmin(1234569L);
        assertNotNull(retrievedAdmin, "Админ не найден после добавления");
        assertEquals(1234569L, retrievedAdmin.getUserId(), "ID админа не совпадает");
    }

    @Test
    public void testSetAndGetSubject() {
        // Создаем предмет
        String subjectName = "Математический анализ";
        Subject subject = new Subject(subjectName);
        databaseMethods.setSubject(subject);

        // Проверяем, что предмет был корректно добавлен
        Subject retrievedSubject = databaseMethods.getSubject(subjectName);
        assertNotNull(retrievedSubject, "Предмет не найден после добавления");
        assertEquals(subjectName, retrievedSubject.getSubjectName(), "Название предмета не совпадает");
    }

    @Test
    public void testSetAndGetUser() {
        // Сначала создаем группу, так как user_group не может быть NULL
        MaiGroup group = new MaiGroup("М8О-тест-группа");

        session.flush();

        // Создаем пользователя и связываем с группой
        long userId = 987654L;
        User user = new User();
        user.setMaiGroup(group); // Устанавливаем группу, чтобы избежать NULL в user_group
        databaseMethods.setUser(user);

        // Проверяем, что пользователь был корректно добавлен
        User retrievedUser = databaseMethods.getUser(userId);
        assertNotNull(retrievedUser, "Пользователь не найден после добавления");
        assertEquals(userId, retrievedUser.getUserId(), "ID пользователя не совпадает");
        assertNotNull(retrievedUser.getMaiGroup(), "Группа пользователя не должна быть NULL");
    }

    @Test
    public void testMailingIntegration() {
        // Создаем группу
        MaiGroup group = new MaiGroup("М8О-211Б-21");

        session.flush();

        // Создаем и прикрепляем рассылку к группе
        Mailing mailing = new Mailing("Важное объявление для группы");
        mailing.setMaiGroup(group);
        databaseMethods.setMailing(mailing);

        // Проверяем получение рассылок по группе
        List<Mailing> mailings = databaseMethods.getMailings("М8О-211Б-21");
        assertNotNull(mailings, "Список рассылок не может быть null");
        assertFalse(mailings.isEmpty(), "Список рассылок не должен быть пустым");
        assertEquals("Важное объявление для группы", mailings.get(0).getMessage(),
                "Текст рассылки не совпадает");
    }

    @Test
    public void testSetAndGetHomework() {
        // Создаем предмет
        Subject subject = new Subject("Программирование");
        databaseMethods.setSubject(subject);
        session.flush(); // Сбрасываем изменения, чтобы получить ID предмета

        // Создаем домашнее задание
        String homeworkText = "Реализовать алгоритм сортировки";
        Homework homework = new Homework(homeworkText, subject);
        databaseMethods.setHomework(homework);

        // Проверяем получение домашнего задания по предмету
        List<Homework> homeworks = databaseMethods.getHomeworks(String.valueOf(subject.getId()));
        assertNotNull(homeworks, "Список домашних заданий не может быть null");
        assertFalse(homeworks.isEmpty(), "Список домашних заданий не должен быть пустым");
        assertEquals(homeworkText, homeworks.get(0).getHomework(), "Текст домашнего задания не совпадает");
    }

    @Test
    public void testScheduleIntegration() {
        // Создаем группу
        MaiGroup group = new MaiGroup("М8О-212Б-21");


        // Создаем предмет
        Subject subject = new Subject("Физика");
        databaseMethods.setSubject(subject);
        session.flush();

        // Получаем день недели
        Weekday weekday = databaseMethods.getWeekday("Понедельник");
        assertNotNull(weekday, "День недели не найден");

        // Создаем расписание с указанием ВСЕХ необходимых полей
        Schedule schedule = new Schedule(weekday, 301, (short)0);
        schedule.setMaiGroup(group);
        schedule.setSubject(subject);
        databaseMethods.setSchedule(schedule);

        // Проверяем получение расписания по группе
        Schedule retrievedSchedule = databaseMethods.getSchedule(String.valueOf(group.getId()));
        assertNotNull(retrievedSchedule, "Расписание не найдено после добавления");
        assertEquals(301, retrievedSchedule.getClassroomId(), "ID аудитории не совпадает");
        assertEquals(weekday.getId(), retrievedSchedule.getWeekdayId().getId(), "ID дня недели не совпадает");
        assertTrue(retrievedSchedule.isLecture(), "Флаг лекции не совпадает");
        assertEquals(0, retrievedSchedule.getWeekType(), "Тип недели не совпадает");
    }

    @Test
    public void testGroupUserIntegration() {
        // Создаем группу
        MaiGroup group = new MaiGroup("М8О-213Б-21");

        session.flush();

        // Создаем пользователя и сразу связываем с группой
        long userId = 555555L;
        User user = new User();
        user.setMaiGroup(group); // Важно: устанавливаем группу до сохранения пользователя
        databaseMethods.setUser(user);

        // Проверяем связь пользователя с группой
        User retrievedUser = databaseMethods.getUser(userId);
        assertNotNull(retrievedUser.getMaiGroup(), "Группа пользователя не установлена");
        assertEquals("М8О-213Б-21", retrievedUser.getMaiGroup().getGroup(), "Название группы не совпадает");
    }

    @Test
    public void testComplexGroupIntegration() {
        // Создаем группу и сохраняем её сначала
        MaiGroup group = new MaiGroup("М8О-214Б-21");

        session.flush();

        // Создаем пользователя и связываем с группой
        User user = new User();
        user.setMaiGroup(group);
        databaseMethods.setUser(user);

        // Создаем предмет
        Subject subject = new Subject("Базы данных");
        databaseMethods.setSubject(subject);
        session.flush();

        // Создаем расписание
        Weekday weekday = databaseMethods.getWeekday("Среда");
        Schedule schedule = new Schedule(weekday, 505, (short)1);
        schedule.setSubject(subject);
        schedule.setMaiGroup(group);
        databaseMethods.setSchedule(schedule);

        // Создаем рассылку
        Mailing mailing = new Mailing("Объявление о тесте по БД");
        mailing.setMaiGroup(group);
        databaseMethods.setMailing(mailing);

        // Проверяем связи
        User retrievedUser = databaseMethods.getUser(666666L);
        assertNotNull(retrievedUser.getMaiGroup(), "Группа пользователя не установлена");

        List<Mailing> mailings = databaseMethods.getMailings(group.getGroup());
        assertFalse(mailings.isEmpty(), "Список рассылок не должен быть пустым");

        Schedule retrievedSchedule = databaseMethods.getSchedule(String.valueOf(group.getId()));
        assertNotNull(retrievedSchedule, "Расписание не найдено");
        assertEquals(weekday.getId(), retrievedSchedule.getWeekdayId().getId(), "ID дня недели не совпадает");
        assertEquals(subject.getId(), retrievedSchedule.getSubject().getId(), "ID предмета не совпадает");
    }

    @Test
    public void testGetWeekday() {
        // Проверяем получение дней недели
        Weekday monday = databaseMethods.getWeekday("Понедельник");
        assertNotNull(monday, "День недели 'Понедельник' не найден");
        assertEquals("Понедельник", monday.getDay(), "Название дня недели не совпадает");

        Weekday friday = databaseMethods.getWeekday("Пятница");
        assertNotNull(friday, "День недели 'Пятница' не найден");
        assertEquals("Пятница", friday.getDay(), "Название дня недели не совпадает");
    }

    @Test
    public void testNullForNonExistentAdmin() {
        // Проверяем, что для несуществующего админа возвращается null
        Admin nonExistentAdmin = databaseMethods.getAdmin(99999999L);
        assertNull(nonExistentAdmin, "Для несуществующего админа должен возвращаться null");
    }

    @Test
    public void testHomeworkWithMultipleEntries() {
        // Создаем предмет
        Subject subject = new Subject("Информатика");
        databaseMethods.setSubject(subject);
        session.flush();

        // Создаем несколько домашних заданий для одного предмета
        Homework homework1 = new Homework("Задание 1 по информатике", subject);
        Homework homework2 = new Homework("Задание 2 по информатике", subject);

        databaseMethods.setHomework(homework1);
        databaseMethods.setHomework(homework2);

        // Проверяем, что получаем оба задания
        List<Homework> homeworks = databaseMethods.getHomeworks(String.valueOf(subject.getId()));
        assertEquals(2, homeworks.size(), "Должно быть возвращено два домашних задания");
    }

    @Test
    public void testMultipleMailingsForGroup() {
        // Создаем группу
        MaiGroup group = new MaiGroup("М8О-215Б-21");
        session.flush();

        // Создаем несколько рассылок для одной группы
        Mailing mailing1 = new Mailing("Объявление 1");
        mailing1.setMaiGroup(group);
        Mailing mailing2 = new Mailing("Объявление 2");
        mailing2.setMaiGroup(group);

        databaseMethods.setMailing(mailing1);
        databaseMethods.setMailing(mailing2);

        // Проверяем, что получаем обе рассылки
        List<Mailing> mailings = databaseMethods.getMailings(group.getGroup());
        assertEquals(2, mailings.size(), "Должно быть возвращено две рассылки");
    }
}