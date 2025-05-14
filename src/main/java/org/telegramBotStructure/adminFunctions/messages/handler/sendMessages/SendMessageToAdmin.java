package org.telegramBotStructure.adminFunctions.messages.handler.sendMessages;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegramBotStructure.adminFunctions.adminState.AdminState;
import org.telegramBotStructure.entity.User;

public interface SendMessageToAdmin {
    public void callback(CallbackQuery callbackQuery);
    public void message(Message message);
    public void daysHandler(Message message, AdminState state);
    public void adminChoise(CallbackQuery callbackQuery, String text);
    public void adminScheduleHandler(CallbackQuery callbackQuery, String[] parts);
    public void dayHandler(CallbackQuery callbackQuery, String[] parts);
    public void adminHomeworkHandler(CallbackQuery callbackQuery, String[] parts);
    public void adminMailingHandler(CallbackQuery callbackQuery, String[] parts);
    public void adminCommands(Message message);
    public void successRemoveSubjects(Message message);
    public void errorRemoveSubjects(Message message);
    public void errorAddHomework(Message message);
    public void sendSuccessAddHomework(Message message);
    public void sendMailingAllUsersToGroupAboutHomeworkAdded(User user, String subject);
    public void sendSuccessMailing(Message message);
    public void sendMailingToAllUsersInGroup(User user, String mailing);
    public void sendSuccessDeleteUser(Message message);
    public void sendErrorNumberFormatMessage(Message message);
    public void errorUserNotInYourGroupMessage(Message message);
}
