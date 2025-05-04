package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "holidays")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "holidays_start")
    @Getter
    @Setter
    private String startDate;

    @Column(name = "holidays_end")
    @Getter
    @Setter
    private String endDate;

    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @Getter
    @Setter
    private MaiGroup maiGroup;

    public Holiday() {}

    public Holiday(String startDate, String endDate, MaiGroup maiGroup) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.maiGroup = maiGroup;
    }


    @Override
    public String toString() {
        return "Дата каникул:" +
                " начало='" + startDate + '\'' +
                ", конец='" + endDate;
    }
}
