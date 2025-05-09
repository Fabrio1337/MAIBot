package org.telegramBotStructure.userFunctions.messages.handler;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface UserMessageHandlerInterface {
    public void callback(CallbackQuery callbackQuery);
    public void message(Message message);
    public void setGroup(CallbackQuery callbackQuery);
    public void setCourse(CallbackQuery callbackQuery);
    public void sendStartMessage(Message message);
    public void returnToCourse(CallbackQuery callbackQuery);
    public void returnToMenu(CallbackQuery callbackQuery);
    public void setSubjects(CallbackQuery callbackQuery);
    public void sendMailing(CallbackQuery callbackQuery);
    public void sendHelpMessage(Message message);
    public void sendAdminErrorMessage(Message message);
    public void sendScheduleMessage(Message message);
    public boolean isAdmin(long chatId);
    public void sendHomework(CallbackQuery callbackQuery);
}
