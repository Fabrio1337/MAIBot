package org.telegramBotStructure.adminFunctions.messages.templates.ErrorMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AdminErrorMessagesInterface {
    public SendMessage userIsNotAdmin(long chatId);
}
