package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mailings")
public class Mailing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "message")
    @Getter
    @Setter
    private String message;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "mai_group")
    @Getter
    @Setter
    private MaiGroup maiGroup;

    public Mailing() {}

    public Mailing(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Mailing{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
