package org.telegramBotStructure.userFunctions.messages.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.adminFunctions.messages.templates.ErrorMessages.AdminErrorMessagesInterface;
import org.telegramBotStructure.adminFunctions.words.AdminWordsInterface;
import org.telegramBotStructure.bot.Client;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.User;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;
import org.telegramBotStructure.userFunctions.messages.templates.ExecutedMessages.UserTemplateMessagesInterface;
import org.telegramBotStructure.userFunctions.words.UserWordsInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserMessageHandler implements UserMessageHandlerInterface {

    private final AdminWordsInterface adminWordsInterface;

    private final AdminErrorMessagesInterface adminErrorMessagesInterface;

    private final Client client;

    private final UserWordsInterface userWordsInterface;

    private final DatabaseMethods databaseMethods;

    private final UserTemplateMessagesInterface userTemplateMessagesInterface;

    private final UserButtonsInterface userButtonsInterface;


    @Override
    public void callback(CallbackQuery callbackQuery) {

        if(callbackQuery.getData().matches("^course_\\d+$"))
        {
            try {
                telegram().execute(
                        userButtonsInterface.setGroupButtons(
                                callbackQuery.getFrom().getId(),
                        callbackQuery.getMessage().getMessageId(),
                        callbackQuery.getData()
                        )
                );
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (callbackQuery.getData().matches("^МСО-[1-5]\\d{2}(Бк|Б|C)$")) {
            try {
                registerUser(callbackQuery);
                telegram().execute(
                        userTemplateMessagesInterface.sendAutorizeMessage(
                                callbackQuery.getFrom().getId(),
                                callbackQuery.getFrom().getUserName()
                        )
                );
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void message(Message message) {
        if(adminWordsInterface.allWords().contains(message.getText())) {
            try {
                telegram().execute(adminErrorMessagesInterface.userIsNotAdmin(message.getChatId()));
            } catch (TelegramApiException e)
            {
                e.printStackTrace();
            }
        }
        else if(userWordsInterface.startWords().contains(message.getText()))
        {

        }
        else if (userWordsInterface.userWords().contains(message.getText()))
        {

        }
        else if (userWordsInterface.scheduleWords().contains(message.getText()))
        {

        }
        else if (userWordsInterface.homeWorkWords().contains(message.getText()))
        {

        }
    }

    public void sendStartMessage(Message message) {
        if(databaseMethods.getUser(message.getChatId()) == null)
        {
            try
            {
                telegram().execute(userTemplateMessagesInterface.sendAutorizeMessage(message.getChatId(), message.getFrom().getFirstName()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else {
            try
            {
                telegram().execute(userTemplateMessagesInterface.sendStartMessage(message.getChatId(), message.getFrom().getFirstName()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerUser(CallbackQuery callbackQuery) {
        MaiGroup maiGroup = new MaiGroup(callbackQuery.getData());
        User user = new User(callbackQuery.getFrom().getId(), maiGroup);
        databaseMethods.setUser(user);
        databaseMethods.setGroup(maiGroup);
    }

    private TelegramClient telegram() {
        return client.getTelegramClient();
    }
}
