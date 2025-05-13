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

    @Column(name = "lesson_number")
    @Getter
    @Setter
    private int lessonNumber;

    @Column(name = "week_type")
    @Getter
    @Setter
    private short weekType;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE},
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

    public Schedule(Weekday weekdayId, int classroomId, short weekType, int lessonNumber) {
        this.weekdayId = weekdayId;
        this.classroomId = classroomId;
        this.weekType = weekType;
        this.lessonNumber = lessonNumber;
    }

    @Override
    public String toString() {
        return "Предмет: " + subject.getSubjectName() +
                " , аудитория: " + classroomId +
                "неделя(0 - каждую неделю, 1 - нечетная неделя, 2 - четная неделя):" + weekType;
    }
}
