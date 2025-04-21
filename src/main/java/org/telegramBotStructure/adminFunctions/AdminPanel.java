package org.telegramBotStructure.adminFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;

@Service
public class AdminPanel implements AdminCommands{

    private final DatabaseMethods databaseMethods;

    @Autowired
    public AdminPanel(DatabaseMethods databaseMethods) {
        this.databaseMethods = databaseMethods;
    }

    @Override
    public SendMessage adminCommands() {
        return null;
    }

    @Override
    public SendMessage addSubjects() {
        return null;
    }

    @Override
    public SendMessage removeSubjects() {
        return null;
    }

    @Override
    public SendMessage addHomework() {
        return null;
    }

    @Override
    public SendMessage removeHomework() {
        return null;
    }

    @Override
    public SendMessage addMailing() {
        return null;
    }

    @Override
    public SendMessage removeMailing() {
        return null;
    }

    @Override
    public SendMessage addTimetable() {
        return null;
    }

    @Override
    public SendMessage removeTimetable() {
        return null;
    }
}
