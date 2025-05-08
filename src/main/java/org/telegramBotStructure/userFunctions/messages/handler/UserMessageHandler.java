package org.telegramBotStructure.userFunctions.messages.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.adminFunctions.messages.templates.errorMessages.AdminErrorMessagesInterface;
import org.telegramBotStructure.adminFunctions.words.AdminWordsInterface;
import org.telegramBotStructure.bot.Client;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.User;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;
import org.telegramBotStructure.userFunctions.messages.templates.executedMessages.UserTemplateMessagesInterface;
import org.telegramBotStructure.userFunctions.words.UserWordsInterface;

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
            setCourse(callbackQuery);
        }
        else if (callbackQuery.getData().matches("^МСО-[1-5]\\d{2}(Бк|Б|C)$")) {
           setGroup(callbackQuery);
        }
        else if (callbackQuery.getData().equals("back_to_course"))
        {
            returnToCourse(callbackQuery);
        }
        else if (callbackQuery.getData().equals("back_to_menu"))
        {
            returnToMenu(callbackQuery);
        }
        else if (callbackQuery.getData().equals("Расписание"))
        {

        }
        else if (callbackQuery.getData().equals("ДЗ"))
        {

        }
        else if (callbackQuery.getData().equals("Информация"))
        {

        }

    }

    @Override
    public void message(Message message) {
        try
        {
            if(adminWordsInterface.allWords().contains(message.getText())) {
                telegram().execute(adminErrorMessagesInterface.userIsNotAdmin(message.getChatId()));
            }
            else if(userWordsInterface.startWords().contains(message.getText()))
            {
                sendStartMessage(message);
            }
            else if (userWordsInterface.userWords().contains(message.getText()))
            {
                telegram().execute(userTemplateMessagesInterface.sendCommandsMessage(message.getChatId(), message.getFrom().getUserName()));
            }
            else if (userWordsInterface.scheduleWords().contains(message.getText()))
            {
                telegram().execute(userTemplateMessagesInterface.
                        sendScheduleMessage(message.getChatId(), databaseMethods.getUser(message.getChatId()).getMaiGroup()));
            }
            else
            {
                telegram().execute(userTemplateMessagesInterface.sendCommandsMessage(message.getChatId(), message.getFrom().getUserName()));
            }
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setCourse(CallbackQuery callbackQuery) {
        try {
            telegram().execute(
                    userButtonsInterface.setGroupButtons(
                            callbackQuery.getMessage().getChatId(),
                            callbackQuery.getMessage().getMessageId(),
                            callbackQuery.getData()
                    )
            );
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGroup(CallbackQuery callbackQuery) {
        try {
            registerUser(callbackQuery);
            telegram().execute(
                    DeleteMessage.builder()
                            .chatId(callbackQuery.getFrom().getId())
                            .messageId(callbackQuery.getMessage().getMessageId())
                            .build()
            );
            telegram().execute(
                    userTemplateMessagesInterface.sendStartMessage(
                            callbackQuery.getFrom().getId(),
                            callbackQuery.getFrom().getUserName()
                    )
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnToCourse(CallbackQuery callbackQuery) {
        try {
            telegram().execute(userButtonsInterface.returnCoursesButtons(
                            callbackQuery.getMessage().getChatId(),
                            callbackQuery.getMessage().getMessageId()
                    )
            );
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnToMenu(CallbackQuery callbackQuery) {
        try
        {
            telegram().execute(userButtonsInterface.returnToMenuButtons(
                    callbackQuery.getMessage().getChatId(),
                    callbackQuery.getMessage().getMessageId()
            ));
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendStartMessage(Message message) {
        if(databaseMethods.getUser(message.getChatId()) == null)
        {
            try
            {
                telegram().execute(userTemplateMessagesInterface.sendAutorizeMessage(message.getChatId(), message.getFrom().getUserName()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else {
            try
            {
                telegram().execute(userTemplateMessagesInterface.sendStartMessage(message.getChatId(), message.getFrom().getUserName()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    protected void registerUser(CallbackQuery callbackQuery) {
        MaiGroup maiGroup = new MaiGroup(callbackQuery.getData());
        User user = new User(callbackQuery.getFrom().getId(), maiGroup);
        databaseMethods.setUser(user);
    }

    private TelegramClient telegram() {
        return client.getTelegramClient();
    }
}
