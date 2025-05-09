package org.telegramBotStructure.userFunctions.messages.templates.errorMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface UserErrorMessagesInterface {
    public SendMessage sendNullSubjectsMessage(long chatId);
    public SendMessage sendCommandsMessage(long chatId, String username);
    public SendMessage sendNullMailingMessage(long chatId);
    public SendMessage sendNullHomeworkMessage(long chatId);
}
