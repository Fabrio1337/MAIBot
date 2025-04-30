package org.telegramBotStructure.userFunctions.messages.handler;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface UserMessageHandlerInterface {
    public void callback(CallbackQuery callbackQuery);
    public void message(Message message);
}
