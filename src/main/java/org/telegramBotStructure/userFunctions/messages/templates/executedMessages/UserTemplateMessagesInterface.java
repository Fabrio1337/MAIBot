package org.telegramBotStructure.userFunctions.messages.templates.executedMessages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.Subject;

public interface UserTemplateMessagesInterface {
    public SendMessage sendStartMessage(long chatId, String username);
    public SendMessage sendAutorizeMessage(long chatId, String username);
    public SendMessage sendCommandsMessage(long chatId, String username);
    public SendMessage sendHomeworkMessage(long chatId, Subject subject);
    public SendMessage sendScheduleMessage(long chatId, MaiGroup maiGroup);
}
