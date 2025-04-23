package org.telegramBotStructure.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "subject_name")
    private String subjectName;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "subject",fetch = FetchType.LAZY)
    private List<Homework> homeworks = new ArrayList<>();;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "subject",fetch = FetchType.EAGER)
    private List<Schedule> schedules = new ArrayList<>();;

    public Subject() {}

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void addHomework(Homework homework) {
        if (homeworks == null) homeworks = new ArrayList<Homework>();
        homeworks.add(homework);

        homework.setSubject(this);
    }

    public void addSchedule(Schedule schedule) {
        if (schedules == null) schedules = new ArrayList<>();
        schedules.add(schedule);

        schedule.setSubject(this);
    }

    public void removeHomework(Homework homework) {
        if(homeworks == null) return;
        homeworks.remove(homework);
    }

    public void removeSchedule(Schedule schedule) {
        if(schedules == null) return;
        schedules.remove(schedule);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectName='" + subjectName + '\'' +
                ", homeworks=" + homeworks +
                ", schedules=" + schedules +
                '}';
    }
}
