package org.telegramBotStructure.DatabaseDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telegramBotStructure.entity.*;

import java.util.List;
import java.util.Objects;

@Repository
public class DatabaseMethodsImpl implements DatabaseMethods{

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Admin getAdmin(long chatId) {
        Session session = sessionFactory.getCurrentSession();
        Admin admin = session.createQuery("from Admin admin where userId= :chatId", Admin.class)
                .setParameter("chatId", chatId)
                .uniqueResult();
        if(admin == null)
        {
            return null;
        }
        else {
            return admin;
        }
    }

    @Override
    public Subject getSubject(String subject) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Subject subject where subject.subjectName= :subject", Subject.class)
                .setParameter("subject", subject)
                .uniqueResult();
    }

    @Override
    public User getUser(long chatId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User user where userId= :chatId", User.class)
                .setParameter("chatId",chatId)
                .uniqueResult();

    }

    @Override
    public List<Mailing> getMailings(String groupName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Mailing mailing where mailing.maiGroup.group= :groupId", Mailing.class)
                .setParameter("groupId", groupName)
                .list();
    }

    @Override
    public Weekday getWeekday(String day) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Weekday weekday where weekday.day= :day", Weekday.class).setParameter("day", day)
                .uniqueResult();
    }



    @Override
    public List<Homework> getHomeworks(String subjectName)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Homework homework where homework.subject.id= :subjectName", Homework.class)
                .setParameter("subjectName", subjectName)
                .list();
    }

    @Override
    public Schedule getSchedule(String group) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Schedule schedule where schedule.maiGroup.id= :group", Schedule.class)
                .setParameter("group", group)
                .uniqueResult();
    }

    @Override
    public Holiday getHoliday(String groupName) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery(
                        "FROM Holiday h WHERE h.maiGroup.group = :groupName", Holiday.class)
                .setParameter("groupName", groupName)
                .uniqueResult();
    }

    @Override
    public Exam getExam(String group) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM Exam e WHERE e.maiGroup.group = :group", Exam.class)
                .setParameter("group", group)
                .uniqueResult();
    }

    @Override
    public void setAdmin(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(admin);
    }

    @Override
    public void setSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(subject);
    }


    @Override
    public void setUser(User user) {
        Session session = sessionFactory.getCurrentSession();

        if (user.getMaiGroup() != null) {
            MaiGroup groupToCheck = user.getMaiGroup();
            MaiGroup existingGroup = null;


            if (Objects.equals(groupToCheck.getId(), null)) {
                existingGroup = session.get(MaiGroup.class, groupToCheck.getId());
            }

            else if (groupToCheck.getGroup() != null && !groupToCheck.getGroup().isEmpty()) {
                try {
                    String hql = "FROM MaiGroup WHERE group = :groupName";
                    existingGroup = session.createQuery(hql, MaiGroup.class)
                            .setParameter("groupName", groupToCheck.getGroup())
                            .uniqueResult();
                } catch (Exception e) {
                    System.err.println("Ошибка при поиске группы: " + e.getMessage());
                }
            }

            if (existingGroup != null) {
                user.setMaiGroup(existingGroup);
            }
        }

        session.merge(user);
    }

    @Override
    public void setMailing(Mailing mailing) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(mailing);
    }

    @Override
    public void setHomework(Homework homework) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(homework);
    }

    @Override
    public void setSchedule(Schedule schedule) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(schedule);
    }

    @Override
    public void setHoliday(Holiday holiday) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(holiday);
    }

    @Override
    public void setExam(Exam exam) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(exam);
    }

    @Override
    public void removeUser(long chatId) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(getUser(chatId));
    }

    @Override
    public void removeSchedule(String day, String group) {
        Session session = sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery(
                "DELETE FROM Schedule schedule WHERE schedule.weekdayId.day = :dayName AND schedule.maiGroup.group = :groupName");
        query.setParameter("dayName", day);
        query.setParameter("groupName", group);
        query.executeUpdate();
    }

    @Override
    public void removeSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(subject);
    }
}
