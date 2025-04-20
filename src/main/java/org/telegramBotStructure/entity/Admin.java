package org.telegramBotStructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "is_set_admins")
    private boolean is_set_admins;

    public Admin() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isIs_set_admins() {
        return is_set_admins;
    }

    public void setIs_set_admins(boolean is_set_admins) {
        this.is_set_admins = is_set_admins;
    }
}
