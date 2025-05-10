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
                "/addHomework 'предмет' 'домашнее задание' - команда для добавления домашнего задания\n" +
                "/addSubject 'предмет' - команда для добавления предмета\n" +
                "/addMailing 'сообщение' - команда для добавления рассылки\n" +
                "/addSchedule 'расписание' - команда для добавления расписания\n" +
                "/removeSubject 'предмет' - команда для удаления предмета\n" +
                "/removeSchedule - команда для удаления расписания\n" +
                "/removeUser 'userId' - команда для удаления пользователя из группы", username);
        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendSuccessDeleteUser(long chatId)
    {
        String text = "Пользователь удален из вашей группы☑\uFE0F";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendSuccessMailing(long chatId)
    {
        String text = "Рассылка успешно отправлена☑\uFE0F";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendSuccessHomework(long chatId)
    {
        String text = "Домашнее задание успешно добавлено☑\uFE0F";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendSuccessSubject(long chatId)
    {
        String text = "Предмет успешно добавлен в вашу группу☑\uFE0F";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendSuccessDeleteSubject(long chatId)
    {
        String text = "Предмет успешно удален из вашей группы☑\uFE0F";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendHomeworkMailingToAllUsersInGroup(long chatId, String subject)
    {
        String text = String.format("Добавлено новое домашнее задание по предмету '%s'", subject);

        return SendMessage.builder().chatId(chatId).text(text).build();
    }


    @Override
    public SendMessage sendMailingToAllUsersInGroup(long chatId, String mailing)
    {
        return SendMessage.builder().chatId(chatId).text(mailing).build();
    }
}
