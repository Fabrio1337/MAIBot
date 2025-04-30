package org.telegramBotStructure.adminFunctions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;
import org.telegramBotStructure.userFunctions.user.UserPanel;

@Service
public class AdminPanel extends UserPanel implements AdminCommands{

    private final DatabaseMethods databaseMethods;

    @Autowired
    public AdminPanel(DatabaseMethods databaseMethods, UserButtonsInterface userButtonsInterface) {
        super(userButtonsInterface);
        this.databaseMethods = databaseMethods;
    }

    @Override
    @Transactional
    public SendMessage adminCommands() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addSubjects() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeSubjects() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addHomework() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeHomework() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addMailing() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeMailing() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addTimetable() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeTimetable() {
        return null;
    }
}
