package org.telegramBotStructure.adminFunctions.messages.templates.executedMessages;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class AdminExecutedMessages implements AdminExecutedMessagesInterface{

    @Override
    public SendMessage sendCommandsMessage(long chatId, String username)
    {
        String text = String.format("%s, Вы можете использовать бота:\n" +
                "1. Через интерактивные кнопки под сообщениями\n" +
                "2. Через команды которые написаны после всех пунктов(весь функционал доступен через интерактивные кнопки)\n" +
                "3. Через те же команды, но через специальное 'меню' которое находится слева от прикрепления файлов" +
                "(весь функционал доступен через интерактивные кнопки)\n\n" +
                "Список команд пользователя\uD83E\uDD10:\n" +
                "/help - показывает список команд\n" +
                "/schedule - показывает расписание\n" +
                "/start - показывает стартовое сообщение с навигационными кнопками\n\n" +
                "Список команд старосты\uD83D\uDE0E:\n" +
                "/admin - показывает список команд для старосты\n" +
                "/addHomework 'домашнее задание' - команда для добавления домашнего задания\n" +
                "/addSubject 'предмет' - команда для добавления предмета\n" +
                "/addMailing 'сообщение' - команда для добавления рассылки\n" +
                "/addSchedule 'расписание' - команда для добавления расписания\n" +
                "/removeSubject 'предмет' - команда для удаления предмета\n" +
                "/removeSchedule - команда для удаления расписания\n" +
                "/removeUser 'userId' - команда для удаления пользователя из группы", username);
        return SendMessage.builder().chatId(chatId).text(text).build();
    }
}
