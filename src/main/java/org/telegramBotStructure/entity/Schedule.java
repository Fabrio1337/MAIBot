package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "weekday_id")
    @Getter
    @Setter
    private Weekday weekdayId = new Weekday();

    @Column(name = "classroom_id")
    @Getter
    @Setter
    private int classroomId;

    @Column(name = "is_lecture")
    @Getter
    @Setter
    private boolean isLecture;

    @Column(name = "week_type")
    @Getter
    @Setter
    private short weekType;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    @Getter
    @Setter
    private Subject subject;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    @Getter
    @Setter
    private MaiGroup maiGroup;

    public Schedule() {}

    public Schedule(Weekday weekdayId, int classroomId, boolean isLecture, short weekType) {
        this.weekdayId = weekdayId;
        this.classroomId = classroomId;
        this.isLecture = isLecture;
        this.weekType = weekType;
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
