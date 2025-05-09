package org.telegramBotStructure.adminFunctions.messages.templates.executedMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AdminExecutedMessagesInterface {
    public SendMessage sendCommandsMessage(long chatId, String username);
}
