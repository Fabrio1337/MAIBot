package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "exam_name")
    @Getter
    @Setter
    private String examName;

    @Column(name = "exam_date")
    @Getter
    @Setter
    private String date;

    @Column(name = "cabinet")
    @Getter
    @Setter
    private int cabinet;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private MaiGroup maiGroup;

    public Exam() {}

    public Exam(String examName, String date, int cabinet, MaiGroup maiGroup) {
        this.examName = examName;
        this.date = date;
        this.cabinet = cabinet;
        this.maiGroup = maiGroup;
    }


    @Override
    public String toString() {
        return "Экзамен: " +
                "Предмет='" + examName + '\'' +
                ", дата экзамена='" + date + '\'' +
                ", кабинет=" + cabinet;
    }
}
