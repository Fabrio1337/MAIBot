package org.telegramBotStructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
