package org.telegramBotStructure.adminFunctions.messages.templates.errorMessages;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ErrorMessages implements ErrorMessagesInterface {

    @Override
    public SendMessage userIsNotAdmin(long chatId) {
        String text = "❌Вы не являетесь старостой, обратитесь к старосте, если нужно добавить/изменить какую либо информацию❌";
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        return message;
    }

    @Override
    public SendMessage sendErrorNumberFormatMessage(long chatId)
    {
        String text = "⚠️ Вы ввели неверный формат userId. \n" +
                "👤 Чтобы узнать userId другого пользователя, перешлите его сообщение этому боту: @getmyid_bot.\n" +
                "🔍 Необходимое id будет указано после надписи 'Forwarded from: userId'.";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage errorUserNotInYourGroupMessage(long chatId)
    {
        String text = "❌ Вы не можете удалить пользователя, который не состоит в вашей группе.\n" +
                "Если вы считаете, что этот пользователь не должен быть в группе, из которой вы пытаетесь его удалить, пожалуйста, напишите об этом @fxrzee.";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage errorAddHomeworkToGroup(long chatId)
    {
        String text = "❌ Не удалось добавить домашнее задание: предмет не найден в вашей группе.";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage errorAddSubjectToGroup(long chatId)
    {
        String text = "❌ Не удалось добавить предмет: такой предмет уже есть в вашей группе.";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage errorDeleteubjectToGroup(long chatId)
    {
        String text = "❌ Не удалось удалить предмет: такого предмета нет в вашей вашей группе.";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendErrorScheduleMessage(long chatId, String day)
    {
        String text = String.format("❌Неверный формат расписания на %s, отправьте сообщение в следующем формате:\n" +
                "1. 'Предмет', 'аудитория', 'неделя'\n" +
                "2. 'Предмет', 'аудитория', 'неделя'\n" +
                "3. 'Предмет', 'аудитория', 'неделя'\n" +
                "4. 'Предмет', 'аудитория', 'неделя'\n" +
                "5. 'Предмет', 'аудитория', 'неделя'\n" +
                "6. 'Предмет', 'аудитория', 'неделя'\n" +
                "7. 'Предмет', 'аудитория', 'неделя'\n" +
                "❗неделя(0 - каждую неделю, 1 - нечетная неделя, 2 - четная неделя)\n" +
                "\uD83E\uDDE0 Цифра перед предметом — это номер пары (от 1 до 7).\n" +
                "\n" +
                "❗Если пара отсутствует — просто указывайте номер и ничего больше.\n" +
                "\n" +
                "\uD83D\uDCCC Пример:\n" +
                "1. Информатика, 302, 0\n" +
                "2.\n" +
                "3. Физика, 105, 2\n" +
                "4.\n" +
                "5. История, 210, 1", day);

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage errorDeleteUser(long chatId)
    {
        String text = "❌Не удалось удалить пользователя, возможно данного пользователя не существует или он не выбрал группу";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage errorDeleteSchedule(long chatId, String day) {
        String text = String.format("❌Не удалось удалить расписание за %s", day);

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendAddNullSubjectsMessage(long chatId)
    {
        String text = "Для вашей группы не добавлено никаких предметов, " +
                "сперва добавьте расписание, предметы добавятся сами";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }
    @Override
    public SendMessage sendRemoveNullSubjectsMessage(long chatId)
    {
        String text = "Для вашей группы нельзя удалить домашние задания, " +
                "сперва добавьте расписание, предметы добавятся сами";

        return SendMessage.builder().chatId(chatId).text(text).build();
    }
}
