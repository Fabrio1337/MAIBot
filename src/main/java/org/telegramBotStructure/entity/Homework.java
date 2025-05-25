package org.telegramBotStructure.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "homeworks")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "homework")
    @Getter
    @Setter
    private String homework;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    @Getter
    @Setter
    private Subject subject;

    public Homework() {}

    public Homework(String homework) {
        this.homework = homework;
    }

    @Override
    public String toString() {
        return "Домашнее задание: " + homework + "\n";
    }
}
