package org.telegramBotStructure.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "weekdays")
public class Weekday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "day_name")
    private String day;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "weekdayId", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    public Weekday() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Weekday{" +
                "day='" + day + '\'' +
                '}';
    }
}
