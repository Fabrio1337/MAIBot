package org.telegramBotStructure.adminFunctions.messages.handler;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface AdminMessageHandlerInterface {
    public void callback(CallbackQuery callbackQuery);
    public void message(Message message);
}
