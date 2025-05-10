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

}
