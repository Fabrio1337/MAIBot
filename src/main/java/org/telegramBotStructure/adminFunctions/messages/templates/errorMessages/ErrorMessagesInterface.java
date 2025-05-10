package org.telegramBotStructure.adminFunctions.messages.templates.errorMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ErrorMessagesInterface {
    public SendMessage userIsNotAdmin(long chatId);
    public SendMessage sendErrorNumberFormatMessage(long chatId);
    public SendMessage errorUserNotInYourGroupMessage(long chatId);
    public SendMessage errorAddHomeworkToGroup(long chatId);
    public SendMessage errorAddSubjectToGroup(long chatId);
    public SendMessage errorDeleteubjectToGroup(long chatId);
}
