package org.telegramBotStructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "mai_groups")
public class MaiGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "mai_group")
    @Getter
    @Setter
    private String group;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<User> users = new ArrayList<>(); ;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Mailing> mailings = new ArrayList<>();;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Schedule> schedules = new ArrayList<>();;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "subject_group_link",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @Getter
    @Setter
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Exam> exams = new ArrayList<>();

    @OneToOne(mappedBy = "maiGroup")
    @Getter
    @Setter
    private Holiday holiday;

    public MaiGroup() {}

    public MaiGroup(String group) {
        this.group = group;
    }

    public void addSubjectToGroup(Subject subject) {
        if(subjects == null) subjects = new ArrayList<>();
        if(!subjects.contains(subject)) {
            subjects.add(subject);

            subject.getMaiGroups().add(this);
        }
    }

    public void removeSubjectFromGroup(Subject subject) {
        if(subjects != null && subjects.contains(subject)) {
            subjects.remove(subject);

            subject.getMaiGroups().remove(this);
        }
    }

    public void addUserToGroup(User user) {
        if(users == null)  users = new ArrayList<User>();
        users.add(user);

        user.setMaiGroup(this);
    }

    public void removeUserFromGroup(User user) {
        if(users == null) return;
        users.remove(user);
        user.setMaiGroup(null);
    }

    public void addMailing(Mailing mailing) {
        if (mailings == null) mailings = new ArrayList<>();
        mailings.add(mailing);

        mailing.setMaiGroup(this);
    }

    public void removeMailing(Mailing mailing) {
        if (mailings == null) return;
        mailings.remove(mailing);
    }

    public void addSchedule(Schedule schedule) {
        if (schedules == null) schedules = new ArrayList<>();
        schedules.add(schedule);

        schedule.setMaiGroup(this);
    }

    public void removeSchedule(Schedule schedule) {
        if (schedules == null) return;
        schedules.remove(schedule);
    }

    public String getAllSchedules() {

        if (schedules == null || schedules.isEmpty()) {
            return "Расписание отсутствует";
        }

        String[][] lessonTimes = {
                {"09:00", "10:30"}, // 1-я пара
                {"10:45", "12:15"}, // 2-я пара
                {"13:00", "14:30"}, // 3-я пара
                {"14:45", "16:15"}, // 4-я пара
                {"16:30", "18:00"}, // 5-я пара
                {"18:00", "19:30"}, // 6-я пара
                {"19:40", "21:10"}  // 7-я пара
        };

        Map<Integer, List<Schedule>> schedulesByDay = new HashMap<>();
        Map<Integer, String> dayNames = new HashMap<>();

        for (Schedule schedule : schedules) {
            int dayId = schedule.getWeekdayId().getId();
            String dayName = schedule.getWeekdayId().getDay();

            if (!schedulesByDay.containsKey(dayId)) {
                schedulesByDay.put(dayId, new ArrayList<>());
                dayNames.put(dayId, dayName);
            }

            schedulesByDay.get(dayId).add(schedule);
        }

        StringBuilder result = new StringBuilder("Расписание группы " + group + ":\n\n");

        for (int dayId = 1; dayId <= 7; dayId++) {
            if (schedulesByDay.containsKey(dayId)) {
                List<Schedule> daySchedules = schedulesByDay.get(dayId);

                result.append(dayNames.get(dayId)).append(":\n");

                Schedule[] lessonsByNumber = new Schedule[7];

                for (Schedule schedule : daySchedules) {

                    int count = schedule.getLessonNumber();

                    if (count >= 1 && count <= 7) {
                        lessonsByNumber[count - 1] = schedule;
                    }
                }

                for (int i = 0; i < 7; i++) {
                    int lessonNumber = i + 1;
                    if (lessonsByNumber[i] != null) {
                        Schedule schedule = lessonsByNumber[i];
                        result.append(lessonNumber).append(". ")
                                .append(lessonTimes[i][0]).append("-").append(lessonTimes[i][1]).append(" ")
                                .append(formatScheduleItem(schedule)).append("\n");
                    } else {
                        result.append(lessonNumber).append(". ")
                               .append(lessonTimes[i][0]).append("-").append(lessonTimes[i][1])
                               .append(" - \n");
                    }
                }

                result.append("\n");
            }
        }

        return result.toString();
    }

    private String formatScheduleItem(Schedule schedule) {
        String weekTypeStr;
        switch (schedule.getWeekType()) {
            case 0:
                weekTypeStr = "каждую неделю";
                break;
            case 1:
                weekTypeStr = "нечетная неделя";
                break;
            case 2:
                weekTypeStr = "четная неделя";
                break;
            default:
                weekTypeStr = "неизвестно";
        }

        return String.format("'%s', аудитория: '%d', %s",
                schedule.getSubject().getSubjectName(),
                schedule.getClassroomId(),
                weekTypeStr);
    }

    @Override
    public String toString() {
        return "MaiGroup{" +
                "id=" + id +
                ", group='" + group + '\'' +
                ", users=" + users +
                ", mailings=" + mailings +
                ", schedules=" + schedules +
                '}';
    }

}
