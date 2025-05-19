package org.telegramBotStructure.adminFunctions.messages.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface AdminMessageHandlerInterface {
    public void callback(CallbackQuery callbackQuery);
    public void message(Message message);
    public void adminCommands(Message message);
    public void removeSubjects(Message message);
    public void addHomework(Message message);
    public void addMailing(Message message);
    public void removeUserFromGroup(Message message);
    public void adminChoice(CallbackQuery callbackQuery, String text);
    public void adminScheduleHandler(CallbackQuery callbackQuery);
    public void adminHomeworkHandler(CallbackQuery callbackQuery);
    public void adminMailingHandler(CallbackQuery callbackQuery);
    public void dayHandler(CallbackQuery callbackQuery);
    public void daysHandler(Message message);
    public void deleteSchedule(CallbackQuery callbackQuery);
    public void addMailingToGroup(Message message);
}
