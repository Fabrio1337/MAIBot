package org.telegramBotStructure.DatabaseDAO;

import org.telegramBotStructure.entity.*;

import java.util.List;

public interface DatabaseMethods {
    public Admin getAdmin(long chatId);
    public Subject getSubject(String subject);
    public User getUser(long chatId);
    public List<Mailing> getMailings(String group);
    public Weekday getWeekday(String day);
    public List<Homework> getHomeworks(String group);
    public Schedule getSchedule(String group);
    public Holiday getHoliday(String group);
    public Exam getExam(String group);
    public void setAdmin(Admin admin);
    public void setSubject(Subject subject);
    public void setUser(User user);
    public void setMailing(Mailing mailing);
    public void setHomework(Homework homework);
    public void setSchedule(Schedule schedule);
    public void setHoliday(Holiday holiday);
    public void setExam(Exam exam);
}
