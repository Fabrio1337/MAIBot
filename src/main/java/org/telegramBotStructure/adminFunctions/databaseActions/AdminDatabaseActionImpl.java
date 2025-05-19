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
    public Subject getSubjectByName(String name) {
        return databaseMethods.getSubjectByName(name);
    }

    @Override
    public void setSubject(Subject subject)
    {
        databaseMethods.setSubject(subject);
    }

    @Override
    public void setSchedule(Schedule schedule, MaiGroup group, Subject subject)
    {
        MaiGroup grp = databaseMethods.mergeMaiGroup(group);

        Subject existing = databaseMethods.getSubject(subject.getSubjectName(), grp.getGroup());

        Subject subj;
        if (existing != null) {
            subj = existing;
        }
        else {
            Subject byName = databaseMethods.getSubjectByName(subject.getSubjectName());
            if (byName != null) {
                subj = byName;
            } else {
                subj = subject;
            }
            subj = databaseMethods.mergeSubject(subj);

            subj.addGroup(grp);
        }

        schedule.setMaiGroup(grp);
        schedule.setSubject(subj);

        Schedule old = databaseMethods.getScheduleWithParametres(schedule);
        if (old != null) {
            old.setSubject(subj);
            databaseMethods.updateSchedule(old);
        }  else {
            databaseMethods.setSchedule(schedule);
        }


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
    public boolean removeSchedule(List<Schedule> allSchedules, String day, long chatId)
    {
        if (allSchedules == null || allSchedules.isEmpty()) return false;

        List<Schedule> toRemove = allSchedules.stream()
                .filter(sch -> sch.getWeekdayId().getDay().equalsIgnoreCase(day))
                .toList();

        if (toRemove.isEmpty()) return false;

        MaiGroup group = null;
        for (Schedule sch : toRemove) {
            if (sch.getMaiGroup() != null) {
                group = sch.getMaiGroup();
                break;
            }
        }

        if (group == null) return false;

        databaseMethods.removeScheduleByDayAndGroup(day, group.getGroup());

        List<Schedule> remainingSchedules = allSchedules.stream()
                .filter(sch -> sch.getWeekdayId() != null && !sch.getWeekdayId().getDay().equalsIgnoreCase(day))
                .toList();

        Set<Subject> removedDaySubjects = toRemove.stream()
                .map(Schedule::getSubject)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Subject> remainingSubjects = remainingSchedules.stream()
                .map(Schedule::getSubject)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Subject> subjectsToRemove = new HashSet<>(removedDaySubjects);
        subjectsToRemove.removeAll(remainingSubjects);


        for (Schedule sch : toRemove) {
            Subject subject = sch.getSubject();
            MaiGroup currentGroup = sch.getMaiGroup();

            boolean subjectUsedInOtherDays = remainingSchedules.stream()
                    .anyMatch(s -> s.getSubject().equals(subject)
                            && s.getMaiGroup().equals(currentGroup));

            if (!subjectUsedInOtherDays) {
                removeSubject(subject, new User(chatId, currentGroup));
            }
        }

        return true;

    }

    @Override
    public void removeSubject(Subject subject, User adminUser)
    {
        if (subject == null || adminUser == null) return;
        MaiGroup adminGroup = adminUser.getMaiGroup();
        if (adminGroup == null) return;

        Subject managed = databaseMethods.getSubjectById(subject.getId());
        if (managed == null) return;

        if (managed.getMaiGroups().remove(adminGroup)) {
            adminGroup.getSubjects().remove(managed);
            databaseMethods.updateSubject(managed);
        }


        boolean noSchedules = managed.getSchedules().isEmpty();
        boolean noGroups    = managed.getMaiGroups().isEmpty();

        if (noSchedules && noGroups) {
            managed.getMaiGroups().clear();
            databaseMethods.updateSubject(managed);

            for (Homework hw : new ArrayList<>(managed.getHomeworks())) {
                databaseMethods.removeHomework(hw);
            }


            databaseMethods.removeSubject(managed);
        }

    }

    @Override
    public boolean removeUser (long chatId)
    {
        if(getCurrentUser(chatId) == null) return false;

        if(getCurrentGroup(chatId) == null) return false;

        User user = getCurrentUser(chatId);

        MaiGroup maiGroup = getCurrentGroup(chatId);
        maiGroup.removeUserFromGroup(user);
        user.setMaiGroup(null);
        databaseMethods.removeUser(user);
        return true;
    }

    @Override
    public void removeMailing(User user)
    {
        databaseMethods.removeMailingByGroup(user.getMaiGroup().getGroup());
    }

}
