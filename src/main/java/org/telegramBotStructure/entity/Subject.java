package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


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

    @OneToMany(    cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH },
            orphanRemoval = true,
            mappedBy = "subject",fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Homework> homeworks = new ArrayList<>();;

    @OneToMany(
            mappedBy = "subject",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
                    CascadeType.PERSIST, CascadeType.REMOVE },
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Getter
    @Setter
    private List<Schedule> schedules = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "subject_group_link",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @Getter
    @Setter
    private Set<MaiGroup> maiGroups = new HashSet<>();


    public Subject() {}

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void addGroup(MaiGroup maiGroup) {
        if(maiGroups == null) maiGroups = new HashSet<>();
        if(!maiGroups.contains(maiGroup)) {
            maiGroups.add(maiGroup);

            if (!maiGroup.getSubjects().contains(this)) {
                maiGroup.getSubjects().add(this);
            }
        }
    }

    public void removeGroup(MaiGroup maiGroup) {
        if(maiGroups != null && maiGroups.contains(maiGroup)) {
            maiGroups.remove(maiGroup);

            if (maiGroup.getSubjects().contains(this)) {
                maiGroup.getSubjects().remove(this);;
            }
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject that = (Subject) o;

        return id != 0 && id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
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
