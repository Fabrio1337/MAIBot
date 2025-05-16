package org.telegramBotStructure.DatabaseDAO;

import org.telegramBotStructure.entity.*;

import java.util.List;

public interface DatabaseMethods {
    public Admin getAdmin(long chatId);
    public Subject getSubject(String subject, String group);
    public User getUser(long chatId);
    public List<Mailing> getMailings(String group);
    public Weekday getWeekday(String day);
    public List<Homework> getHomeworks(String group);
    public List<Schedule> getSchedule(String group);
    public Holiday getHoliday(String group);
    public Exam getExam(String group);
    public Subject getSubjectByName(String subject);
    public Schedule getScheduleWithParametres(Schedule schedule);
    public Subject mergeSubject(Subject subject);
    public MaiGroup mergeMaiGroup(MaiGroup group);
    public Schedule saveSchedule(Schedule schedule);
    public Subject saveSubject(Subject subject);
    public Subject getSubjectById(long id);
    public void setAdmin(Admin admin);
    public void setSubject(Subject subject);
    public void setUser(User user);
    public void setMailing(Mailing mailing);
    public void setHomework(Homework homework);
    public void setSchedule(Schedule schedule);
    public void setHoliday(Holiday holiday);
    public void setExam(Exam exam);
    public void removeUser(User user);
    public void removeSchedule(String day, String group);
    public void removeSubject(Subject subject);
    public void removeHomework(Homework homework);
    public void updateSubject(Subject subject);
    public void updateSchedule(Schedule schedule);
    public void removeScheduleByDayAndGroup(String day, String group);

}
