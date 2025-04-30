package org.telegramBotStructure.userFunctions.messages.templates.ExecutedMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface UserTemplateMessagesInterface {
    public SendMessage sendStartMessage(long chatId, String username);
    public SendMessage sendAutorizeMessage(long chatId, String username);
}
