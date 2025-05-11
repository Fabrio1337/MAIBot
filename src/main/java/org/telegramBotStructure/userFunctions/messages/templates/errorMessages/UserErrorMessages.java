package org.telegramBotStructure.userFunctions.messages.templates.errorMessages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class UserErrorMessages implements UserErrorMessagesInterface{
    @Override
    public SendMessage sendNullSubjectsMessage(long chatId)
    {
        String text = "Для вашей группы не добавлено никаких предметов, " +
                "обратитесь к своему старосте чтобы он добавил предметы";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendNullMailingMessage(long chatId)
    {
        String text = "Для вашей группы не добавлено никаких рассылок, " +
                "обратитесь к своему старосте чтобы он добавил предметы";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }
    @Override
    public SendMessage sendNullHomeworkMessage(long chatId)
    {
        String text = "Для вашей группы не добавлено никаких домашних заданий по выбранному предмету, " +
                "обратитесь к своему старосте чтобы он добавил предметы";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }


    @Override
    public SendMessage sendCommandsMessage(long chatId, String username){
        String text = String.format("%s, Вы можете использовать бота:\n" +
                "1. Через интерактивные кнопки под сообщениями\n" +
                "2. Через команды которые написаны после всех пунктов (весь функционал доступен через интерактивные кнопки)\n" +
                "3. Через те же команды, но через специальное 'меню' которое находится слева от прикрепления файлов" +
                " (весь функционал доступен через интерактивные кнопки)\n\n" +
                "Список команд:\n" +
                "/help - показывает список команд\n" +
                "/schedule - показывает расписание\n" +
                "/start - показывает стартовое сообщение с навигационными кнопками", username);
        return SendMessage.builder().chatId(chatId).text(text).build();
    }

}
