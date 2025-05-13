package org.telegramBotStructure.adminFunctions.messages.templates.executedMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AdminExecutedMessagesInterface {
    public SendMessage sendCommandsMessage(long chatId, String username);
    public SendMessage sendSuccessDeleteUser(long chatId);
    public SendMessage sendSuccessMailing(long chatId);
    public SendMessage sendMailingToAllUsersInGroup(long chatId, String mailing);
    public SendMessage sendSuccessHomework(long chatId);
    public SendMessage sendHomeworkMailingToAllUsersInGroup(long chatId, String subject);
    public SendMessage sendSuccessSubject(long chatId);
    public SendMessage sendSuccessDeleteSubject(long chatId);
    public SendMessage sendWaitingScheduleMessage(long chatId, String day);
}
