package org.telegramBotStructure.userFunctions.messages.templates.executedMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.Subject;

public interface UserTemplateMessagesInterface {
    public SendMessage sendStartMessage(long chatId, String username);
    public SendMessage sendAutorizeMessage(long chatId, String username);
    public SendMessage sendScheduleMessage(long chatId, MaiGroup maiGroup);
    public SendMessage sendMailingMessage(long chatId, MaiGroup maiGroup);
    public SendMessage sendHomeworkMessage(long chatId, Subject subject);
}
