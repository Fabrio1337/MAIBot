package org.telegramBotStructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "weekday_id")
    private Weekday weekdayId;

    @Column(name = "classroom_id")
    private int classroomId;

    @Column(name = "is_lecture")
    private boolean isLecture;

    @Column(name = "week_type")
    private short weekType;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private MaiGroup maiGroup;

    public Schedule() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public boolean isLecture() {
        return isLecture;
    }

    public void setLecture(boolean lecture) {
        isLecture = lecture;
    }

    public short getWeekType() {
        return weekType;
    }

    public void setWeekType(short weekType) {
        this.weekType = weekType;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public MaiGroup getMaiGroup() {
        return maiGroup;
    }

    public void setMaiGroup(MaiGroup maiGroup) {
        this.maiGroup = maiGroup;
    }

    public Weekday getWeekdayId() {
        return weekdayId;
    }

    public void setWeekdayId(Weekday weekdayId) {
        this.weekdayId = weekdayId;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", classroomId=" + classroomId +
                ", isLecture=" + isLecture +
                ", weekType=" + weekType +
                ", subject=" + subject +
                ", maiGroup=" + maiGroup +
                '}';
    }
}
