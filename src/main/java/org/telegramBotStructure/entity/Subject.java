package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "subject_name")
    @Getter
    @Setter
    private String subjectName;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "subject",fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Homework> homeworks = new ArrayList<>();;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "subject",fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<Schedule> schedules = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "subject_group_link",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<MaiGroup> maiGroups = new ArrayList<>();


    public Subject() {}

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void addGroup(MaiGroup maiGroup) {
        if(maiGroups == null) maiGroups = new ArrayList<>();
        if(!maiGroups.contains(maiGroup)) maiGroups.add(maiGroup);
    }

    public void addHomework(Homework homework) {
        if (homeworks == null) homeworks = new ArrayList<>();
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
