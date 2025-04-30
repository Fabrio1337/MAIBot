package org.telegramBotStructure.adminFunctions.admin;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AdminCommands {
    public SendMessage adminCommands();
    public SendMessage addSubjects();
    public SendMessage removeSubjects();
    public SendMessage addHomework();
    public SendMessage removeHomework();
    public SendMessage addMailing();
    public SendMessage removeMailing();
    public SendMessage addTimetable();
    public SendMessage removeTimetable();
}
