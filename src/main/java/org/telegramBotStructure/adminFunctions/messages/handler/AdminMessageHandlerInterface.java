package org.telegramBotStructure.adminFunctions.messages.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface AdminMessageHandlerInterface {
    public void callback(CallbackQuery callbackQuery);
    public void message(Message message);
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
