package org.telegramBotStructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mailings")
public class Mailing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "message")
    private String message;

    public Mailing() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
