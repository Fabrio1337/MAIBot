package org.telegramBotStructure.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "homeworks")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "homework")
    private String homework;

    public Homework() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", homework='" + homework + '\'' +
                '}';
    }
}
