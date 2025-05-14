package org.telegramBotStructure.adminFunctions.databaseActions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.adminFunctions.adminState.AdminState;
import org.telegramBotStructure.entity.*;

import java.util.*;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminDatabaseActionImpl implements AdminDatabaseAction {

    private final DatabaseMethods databaseMethods;

    @Override
    public Weekday getCurrentWeekday(AdminState adminState) {
        return databaseMethods.getWeekday(adminState.getDay());
    }

    @Override
    public MaiGroup getCurrentGroup(long chatId)
    {
        return getCurrentUser(chatId).getMaiGroup();
    }

    @Override
    public User getCurrentUser(long chatId)
    {
        return databaseMethods.getUser(chatId);
    }

    @Override
    public Subject getSubject(String subject, String group)
    {
        return databaseMethods.getSubject(subject, group);
    }

    @Override
    public Admin getUserIsAdmin(long chatId)
    {
        return databaseMethods.getAdmin(chatId);
    }

    @Override
    public List<Schedule> getCurrentSchedule(String group)
    {
        return databaseMethods.getSchedule(group);
    }

    @Override
    public void setSubject(Subject subject)
    {
        databaseMethods.setSubject(subject);
    }

    @Override
    public void setSchedule(Schedule schedule, MaiGroup group, Subject subject)
    {
        subject.addGroup(group);
        subject.addSchedule(schedule);

        schedule.setMaiGroup(group);
        schedule.setSubject(subject);

        group.addSchedule(schedule);
        group.addSubjectToGroup(subject);

        databaseMethods.setSubject(subject);
        databaseMethods.setSchedule(schedule);
    }

    @Override
    public void setHomework(Homework homework)
    {
        databaseMethods.setHomework(homework);
    }

    @Override
    public void setMailing(Mailing mailing)
    {
        databaseMethods.setMailing(mailing);
    }

    @Override
    public void removeSchedule(List<Schedule> allSchedules, String day, long chatId)
    {
        if (allSchedules == null || allSchedules.isEmpty()) return;
        List<Schedule> toRemove = allSchedules.stream()
                .filter(sch -> sch.getWeekdayId().getDay().equalsIgnoreCase(day))
                .toList();
        if (toRemove.isEmpty()) return;

        Set<Subject> touchedSubjects = toRemove.stream()
                .map(Schedule::getSubject)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        for (Schedule sch : toRemove) {
            Subject subj = sch.getSubject();
            if (subj != null) {
                subj.removeSchedule(sch);
            }
            MaiGroup grp = sch.getMaiGroup();
            if (grp != null) {
                grp.removeSchedule(sch);
            }
            sch.setSubject(null);
            sch.setMaiGroup(null);
            sch.setWeekdayId(null);

            databaseMethods.removeSchedule(day, grp.getGroup());
        }

        List<Schedule> remaining = allSchedules.stream()
                .filter(sch -> !toRemove.contains(sch))
                .toList();

        for (Subject subj : touchedSubjects) {
            boolean stillInGroup = remaining.stream()
                    .anyMatch(sch -> {
                        Subject s = sch.getSubject();
                        return s != null && s.getId() == subj.getId();
                    });
            if (!stillInGroup) {
                removeSubject(subj, getCurrentUser(chatId));
            }
        }

    }

    @Override
    public void removeSubject(Subject subject, User adminUser)
    {
        if (subject == null || adminUser == null) return;

        MaiGroup adminGroup = adminUser.getMaiGroup();
        if (adminGroup == null) return;


        if (subject.getMaiGroups().remove(adminGroup)) {
            adminGroup.getSubjects().remove(subject);
            databaseMethods.updateSubject(subject);
        }


        if (subject.getMaiGroups().isEmpty()) {
            for (Homework hw : new ArrayList<>(subject.getHomeworks())) {
                databaseMethods.removeHomework(hw);
            }
            databaseMethods.removeSubject(subject);
        }
    }

    @Override
    public void removeUser (long chatId)
    {
        User user = getCurrentUser(chatId);

        MaiGroup maiGroup = getCurrentGroup(chatId);
        maiGroup.removeUserFromGroup(user);
        user.setMaiGroup(null);
        databaseMethods.removeUser(chatId);

    }




}
