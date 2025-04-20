package org.telegramBotStructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "classroom_id")
    private int classroomId;

    @Column(name = "is_lecture")
    private boolean isLecture;

    @Column(name = "week_type")
    private short weekType;

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

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", classroomId=" + classroomId +
                ", isLecture=" + isLecture +
                ", weekType=" + weekType +
                '}';
    }
}
