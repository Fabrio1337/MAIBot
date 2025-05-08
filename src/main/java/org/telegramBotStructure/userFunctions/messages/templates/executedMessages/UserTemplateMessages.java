package org.telegramBotStructure.userFunctions.messages.templates.executedMessages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.Subject;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserTemplateMessages implements UserTemplateMessagesInterface{

    private final UserButtonsInterface userButtonsInterface;

    @Override
    public SendMessage sendStartMessage(long chatId, String username) {
        String text = String.format("%s, Добро пожаловать в самого лучшего бота для учебы\uD83D\uDCC5!\n" +
                "напиши /commands или /help чтобы узнать какие команды может обрабатывать бот\uD83D\uDCDD", username);
        return SendMessage.builder().chatId(chatId).text(text)
                .replyMarkup(userButtonsInterface.setUserChoiceButtons()).build();
    }

    @Override
    public SendMessage sendAutorizeMessage(long chatId, String username) {
        String text = String.format("%s, Добро пожаловать в самого лучшего бота для учебы\uD83D\uDCC5!\n" +
                "Чтобы начать использовать бота, выберите курс и группу\uD83D\uDE0A", username);

        return SendMessage.builder().chatId(chatId).text(text)
                .replyMarkup(userButtonsInterface.setWelcomeButtons()).build();
    }

    @Override
    public SendMessage sendHomeworkMessage(long chatId, Subject subject){
        String text = String.format("Домашние задания по предмету '%s' :\n"
                + subject.getHomeworks(), subject.getSubjectName());

        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendCommandsMessage(long chatId, String username){
        String text = String.format("%s, Вы можете использовать бота:\n" +
                "1. Через интерактивные кнопки под сообщениями\n" +
                "2. Через команды которые написаны после всех пунктов(весь функционал доступен через интерактивные кнопки)\n" +
                "3. Через те же команды, но через специальное 'меню' которое находится слева от прикрепления файлов" +
                "(весь функционал доступен через интерактивные кнопки)\n\n" +
                "Список команд:\n" +
                "/help - показывает список команд\n" +
                "/schedule - показывает расписание\n" +
                "/start - показывает стартовое сообщение с навигационными кнопками", username);
        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    @Override
    public SendMessage sendScheduleMessage(long chatId, MaiGroup maiGroup) {
        String subjects = maiGroup.getSchedules().stream()
                .map(s -> "- " + s)
                .collect(Collectors.joining("\n"));
        String text = String.format("Расписание %s группы:\n" + subjects, maiGroup.getGroup());

        return SendMessage.builder().chatId(chatId).text(text).build();
    }
}
