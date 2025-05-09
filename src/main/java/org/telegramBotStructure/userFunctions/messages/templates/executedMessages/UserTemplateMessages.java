package org.telegramBotStructure.userFunctions.messages.templates.executedMessages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.Subject;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;
import org.telegramBotStructure.userFunctions.messages.templates.errorMessages.UserErrorMessagesInterface;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserTemplateMessages implements UserTemplateMessagesInterface{

    private final UserButtonsInterface userButtonsInterface;

    private final UserErrorMessagesInterface userErrorMessagesInterface;

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
    public SendMessage sendScheduleMessage(long chatId, MaiGroup maiGroup) {
        if(maiGroup.getSchedules().isEmpty())
        {
            return userErrorMessagesInterface.sendNullSubjectsMessage(chatId);
        }
        else {
            String subjects = maiGroup.getSchedules().stream()
                    .map(s -> "- " + s)
                    .collect(Collectors.joining("\n"));
            String text = String.format("Расписание %s группы:\n" + subjects, maiGroup.getGroup());

            return SendMessage.builder().chatId(chatId).text(text).build();
        }
    }

    @Override
    public SendMessage sendMailingMessage(long chatId, MaiGroup maiGroup) {
        if(maiGroup.getMailings().isEmpty())
        {
            return userErrorMessagesInterface.sendNullMailingMessage(chatId);
        }
        else {
            String mailings = maiGroup.getMailings().stream()
                    .map(s -> "- " + s)
                    .collect(Collectors.joining("\n"));
            String text = String.format("Рассылка %s группы:\n" + mailings, maiGroup.getGroup());

            return SendMessage.builder().chatId(chatId).text(text).build();
        }
    }

    @Override
    public SendMessage sendHomeworkMessage(long chatId, Subject subject) {
        if(subject.getHomeworks().isEmpty())
        {
            return userErrorMessagesInterface.sendNullHomeworkMessage(chatId);
        }
        else {
            String mailings = subject.getHomeworks().stream()
                    .map(s -> "- " + s)
                    .collect(Collectors.joining("\n"));
            String text = String.format("Домашние задания по %s:\n" + mailings, subject.getSubjectName());

            return SendMessage.builder().chatId(chatId).text(text).build();
        }
    }
}
