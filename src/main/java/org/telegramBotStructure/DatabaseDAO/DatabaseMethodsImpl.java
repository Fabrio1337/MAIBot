package org.telegramBotStructure.DatabaseDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telegramBotStructure.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Subject getSubject(String subject, String group) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("""
    select s from Subject s
    join s.maiGroups g
    where s.subjectName = :subjectName and g.group = :groupName
""", Subject.class)
                .setParameter("subjectName", subject)
                .setParameter("groupName", group)
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
    public List<Schedule> getSchedule(String group) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Schedule schedule where schedule.maiGroup.group= :group", Schedule.class)
                .setParameter("group", group).list();
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
    public Schedule getScheduleWithParametres(Schedule schedule)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                """
    select s from Schedule s
    where s.weekdayId = :weekdayId
    and s.classroomId = :classroomId
    and s.weekType = :weekType
    and s.maiGroup = :maiGroup
""", Schedule.class)
                .setParameter("weekdayId", schedule.getWeekdayId())
                .setParameter("classroomId", schedule.getClassroomId())
                .setParameter("weekType", schedule.getWeekType())
                .setParameter("maiGroup", schedule.getMaiGroup())
                .uniqueResult();
    }

    @Override
    public Subject getSubjectByName(String subject) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select s from Subject s where s.subjectName = :name",
                        Subject.class)
                .setParameter("name", subject)
                .uniqueResult();
    }

    @Override
    public Subject getSubjectById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Subject s where s.id = :id ", Subject.class)
                .setParameter("id", id)
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
    public Subject mergeSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(subject);
    }
    @Override
    public MaiGroup mergeMaiGroup(MaiGroup group) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(group);

    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(schedule);
    }

    @Override
    public Subject saveSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(subject);
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

        Subject managedSubject = session.merge(homework.getSubject());

        managedSubject.addHomework(homework);

        session.persist(homework);
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
    public void removeUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(user);
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
    public void removeScheduleByDayAndGroup(String day, String groupName) {
        Session session = sessionFactory.getCurrentSession();

        List<Schedule> toRemove = session.createQuery(
                        "SELECT s FROM Schedule s WHERE s.weekdayId.day = :dayName AND s.maiGroup.group = :groupName",
                        Schedule.class)
                .setParameter("dayName", day)
                .setParameter("groupName", groupName)
                .list();
        if (toRemove.isEmpty()) return;

        Set<Subject> affected = toRemove.stream()
                .map(Schedule::getSubject)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (Schedule sch : toRemove) {
            Subject subj = sch.getSubject();
            MaiGroup grp  = sch.getMaiGroup();
            if (subj != null) {
                subj.removeSchedule(sch);
                subj.removeGroup(grp);
            }
            if (grp != null) {
                grp.removeSchedule(sch);
                grp.removeSubjectFromGroup(subj);
            }
            sch.setSubject(null);
            sch.setMaiGroup(null);
            sch.setWeekdayId(null);
            session.remove(sch);
        }
        session.flush();

        for (Subject s : affected) {
            Subject managed = session.get(Subject.class, s.getId());
            boolean noSched = managed.getSchedules().isEmpty();
            boolean noGroups = managed.getMaiGroups().isEmpty();
            if (noSched && noGroups) {

                for (Homework hw : new ArrayList<>(managed.getHomeworks())) {
                    session.remove(hw);
                }
                session.remove(managed);
            } else {
                session.merge(managed);
            }
        }
    }

    @Override
    public void removeSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(subject);
    }

    @Override
    public void removeHomework(Homework homework) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(homework);
    }

    @Override
    public void updateSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(subject);
    }

    @Override
    public void updateSchedule(Schedule schedule) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(schedule);
    }

    @Override
    public void removeMailingByGroup(String group) {
        Session session = sessionFactory.getCurrentSession();
        List<Mailing> mailings = session.createQuery("select m from Mailing m where m.maiGroup.group =: groupName", Mailing.class)
                .setParameter("groupName", group)
                .list();

        if (mailings.isEmpty()) return;
        for (Mailing mailing : mailings) {
            session.remove(mailing);
        }
    }
}
