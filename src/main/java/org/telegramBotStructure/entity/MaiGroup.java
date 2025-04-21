package org.telegramBotStructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mai_groups")
public class MaiGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "mai_group")
    private String group;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup",
            fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup",
            fetch = FetchType.EAGER)
    private List<Mailing> mailings;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "maiGroup",
            fetch = FetchType.EAGER)
    private List<Schedule> schedules;


    public MaiGroup() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Mailing> getMailings() {
        return mailings;
    }

    public void setMailings(List<Mailing> mailings) {
        this.mailings = mailings;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }



    @Override
    public String toString() {
        return "MaiGroup{" +
                "id=" + id +
                ", group='" + group + '\'' +
                ", users=" + users +
                ", subjects=" + subjects +
                ", mailings=" + mailings +
                ", schedules=" + schedules +
                '}';
    }

}
