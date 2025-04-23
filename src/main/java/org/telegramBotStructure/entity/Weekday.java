package org.telegramBotStructure.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    private List<Schedule> schedules = new ArrayList<>();;

    public Weekday() {}

    public Weekday(String day) {
        this.day = day;
    }

    public void addSchedule(Schedule schedule) {
        if (schedules == null) schedules = new ArrayList<Schedule>();
        schedules.add(schedule);

        schedule.setWeekdayId(this);
    }
    public void removeSchedule(Schedule schedule) {
        if (schedules == null) return;
        schedules.remove(schedule);
    }

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
