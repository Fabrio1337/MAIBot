package org.telegramBotStructure.userFunctions.messages.templates.executedMessages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserTemplateMessages implements UserTemplateMessagesInterface{

    private final UserButtonsInterface userButtonsInterface;

    @Override
    public SendMessage sendStartMessage(long chatId, String username) {
        String text = String.format("%s, Добро пожаловать в самого лучшего бота для учебы\uD83D\uDCC5!\n" +
                "напиши /commands или /help чтобы узнать какие команды может обрабатывать бот\uD83D\uDCDD", username);
        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendAutorizeMessage(long chatId, String username) {
        String text = String.format("%s, Добро пожаловать в самого лучшего бота для учебы\uD83D\uDCC5!\n" +
                "Чтобы начать использовать бота, выберите курс и группу\uD83D\uDE0A", username);

        return SendMessage.builder().chatId(chatId).text(text).replyMarkup(userButtonsInterface.setWelcomeButtons()).build();
    }
}
