package org.telegramBotStructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(name = "user_id")
    @Getter
    @Setter
    private long userId;

    @Column(name = "is_set_admins")
    @Getter
    @Setter
    private boolean is_set_admins;

    public Admin() {}

    public Admin(long userId, boolean is_set_admins) {
        this.userId = userId;
        this.is_set_admins = is_set_admins;
    }

}
