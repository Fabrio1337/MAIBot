package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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

    @ManyToMany(mappedBy = "maiGroups")
    @Getter
    @Setter
    private Set<Subject> subjects = new HashSet<>();

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
        if(subjects == null) subjects = new HashSet<>();
        if(!subjects.contains(subject)) {
            subjects.add(subject);

            if (!subject.getMaiGroups().contains(this)) {
                subject.getMaiGroups().add(this);
            }
        }
    }

    public void removeSubjectFromGroup(Subject subject) {
        if(subjects != null && subjects.contains(subject)) {
            subjects.remove(subject);

            if (subject.getMaiGroups().contains(this)) {
                subject.getMaiGroups().remove(this);
            }
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

        Map<Integer, String> fullWeek = Map.of(
                1, "Понедельник",
                2, "Вторник",
                3, "Среда",
                4, "Четверг",
                5, "Пятница",
                6, "Суббота",
                7, "Воскресенье"
        );

        Map<Integer, List<Schedule>> schedulesByDay = new HashMap<>();

        for (Schedule schedule : schedules) {
            int dayId = schedule.getWeekdayId().getId();
            schedulesByDay.computeIfAbsent(dayId, k -> new ArrayList<>()).add(schedule);
        }

        StringBuilder result = new StringBuilder("Расписание группы " + group + ":\n\n");

        for (int dayId = 1; dayId <= 7; dayId++) {
            result.append("\uD83D\uDDD3").append(fullWeek.get(dayId)).append(":\n");

            List<Schedule> daySchedules = schedulesByDay.get(dayId);

            if (daySchedules == null || daySchedules.isEmpty()) {
                result.append("В этот день пар нет\n");
                result.append("-------------------------\n\n");
                continue;
            }

            Schedule[] lessonsByNumber = new Schedule[7];

            for (Schedule schedule : daySchedules) {
                int count = schedule.getLessonNumber();
                if (count >= 1 && count <= 7) {
                    lessonsByNumber[count - 1] = schedule;
                }
            }

            for (int i = 0; i < 7; i++) {
                int num = i + 1;
                String timeRange = lessonTimes[i][0] + "–" + lessonTimes[i][1];
                result.append(num).append(". ").append(timeRange);

                if (lessonsByNumber[i] != null) {
                    result.append("\n")
                            .append(formatScheduleItem(lessonsByNumber[i]))
                            .append("\n\n");
                } else {
                    result.append("\n")
                            .append("Нет пары")
                            .append("\n\n");
                }
            }
            result.append("-------------------------\n");
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


        return new StringBuilder()
                .append(schedule.getSubject().getSubjectName()).append("\n")
                .append("Ауд.: ").append("'").append(schedule.getClassroomId()).append("'").append("\n")
                .append(weekTypeStr)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaiGroup)) return false;
        MaiGroup that = (MaiGroup) o;
        return id != 0 && id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
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
