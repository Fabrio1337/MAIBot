package org.telegramBotStructure.DatabaseDAO;

import org.telegramBotStructure.entity.*;

public interface DatabaseMethods {
    public Admin getAdmin(long chatId);
    public Subject getSubject(String subjectName);
    public User getUser(long chatId);
    public MaiGroup getGroup(long chatId);
    public Mailing getMailing(String group);
    public Weekday getWeekday(String day);
    public Homework getHomework(String homework);
    public Schedule getSchedule(long id);
    public void setAdmin(Admin admin);
    public void setSubject(Subject subject);
    public void setUser(User user);
    public void setGroup(MaiGroup maiGroup);
    public void setMailing(Mailing mailing);
    public void setHomework(Homework homework);
    public void setSchedule(Schedule schedule);
}
