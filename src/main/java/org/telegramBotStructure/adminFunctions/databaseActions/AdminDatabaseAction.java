package org.telegramBotStructure.adminFunctions.databaseActions;

import org.telegramBotStructure.adminFunctions.adminState.AdminState;
import org.telegramBotStructure.entity.*;

import java.util.List;

public interface AdminDatabaseAction {
    public Weekday getCurrentWeekday(AdminState adminState);
    public MaiGroup getCurrentGroup(long chatId);
    public User getCurrentUser(long chatId);
    public Subject getSubject(String subject, String group);
    public Admin getUserIsAdmin(long chatId);
    public List<Schedule> getCurrentSchedule(String group);
    public Subject getSubjectByName(String name);
    public void setSubject(Subject subject);
    public void setSchedule(Schedule schedule, MaiGroup group, Subject subject);
    public void setHomework(Homework homework);
    public void setMailing(Mailing mailing);
    public boolean removeSchedule(List<Schedule> allSchedules, String day, long chatId);
    public void removeSubject(Subject subject, User adminUser);
    public boolean removeUser (long chatId);

}
