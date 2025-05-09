package org.telegramBotStructure.userFunctions.messages.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.adminFunctions.messages.templates.errorMessages.AdminErrorMessagesInterface;
import org.telegramBotStructure.adminFunctions.words.AdminWordsInterface;
import org.telegramBotStructure.bot.Client;
import org.telegramBotStructure.entity.Admin;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.Subject;
import org.telegramBotStructure.entity.User;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;
import org.telegramBotStructure.userFunctions.messages.templates.errorMessages.UserErrorMessagesInterface;
import org.telegramBotStructure.userFunctions.messages.templates.executedMessages.UserTemplateMessagesInterface;
import org.telegramBotStructure.userFunctions.words.UserWordsInterface;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserMessageHandler implements UserMessageHandlerInterface {

    @Getter
    protected final  AdminWordsInterface adminWordsInterface;

    @Getter
    protected final AdminErrorMessagesInterface adminErrorMessagesInterface;

    @Getter
    protected final Client client;

    @Getter
    protected final UserWordsInterface userWordsInterface;

    @Getter
    protected final DatabaseMethods databaseMethods;

    @Getter
    protected final UserTemplateMessagesInterface userTemplateMessagesInterface;

    @Getter
    protected final UserButtonsInterface userButtonsInterface;

    protected final UserErrorMessagesInterface userErrorMessagesInterface;


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
        else if (!(isAdmin(callbackQuery.getMessage().getChatId())) && callbackQuery.getData().equals("Расписание"))
        {
            sendScheduleMessage((Message) callbackQuery.getMessage());
        }
        else if (!(isAdmin(callbackQuery.getMessage().getChatId())) && callbackQuery.getData().equals("ДЗ"))
        {
            setSubjects(callbackQuery);
        }
        else if (!(isAdmin(callbackQuery.getMessage().getChatId())) && callbackQuery.getData().equals("Информация"))
        {
            sendMailing(callbackQuery);
        }
        else if(callbackQuery.getData().matches("^subject_.*$"))
        {
            sendHomework(callbackQuery);
        }

    }

    @Override
    public void message(Message message) {
        if( !(isAdmin(message.getChatId())) && adminWordsInterface.allWords().contains(message.getText())) {
            sendAdminErrorMessage(message);
        }
        else if(userWordsInterface.startWords().contains(message.getText()))
        {
            sendStartMessage(message);
        }
        else if ( !(isAdmin(message.getChatId())) && userWordsInterface.userWords().contains(message.getText()))
        {
            sendHelpMessage(message);
        }
        else if ( !(isAdmin(message.getChatId())) && userWordsInterface.scheduleWords().contains(message.getText()))
        {
            sendScheduleMessage(message);
        }
        else
        {
            sendHelpMessage(message);
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
    public void setSubjects(CallbackQuery callbackQuery) {
        try {
            if(getUserSubjects(callbackQuery).isEmpty())
            {
                telegram().execute(
                        userErrorMessagesInterface.sendNullSubjectsMessage(
                                callbackQuery.getMessage().getChatId()
                        )
                );
            }
            else {
                telegram().execute(userButtonsInterface.setSubjectButtons(
                        callbackQuery.getMessage().getChatId(),
                        callbackQuery.getMessage().getMessageId(),
                        getUserSubjects(callbackQuery)
                ));
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendHomework(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        Subject subject = getUserSubjects(callbackQuery).stream()
                .filter(s -> s.getSubjectName().equals(parts[1]))
                .findFirst()
                .orElse(null);
        try {
            telegram().execute(
                    userTemplateMessagesInterface.sendHomeworkMessage(
                            callbackQuery.getMessage().getChatId(),
                            subject
                    )
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailing(CallbackQuery callbackQuery)
    {
        try {
            telegram().execute(
                    userTemplateMessagesInterface.sendMailingMessage(
                             callbackQuery.getMessage().getChatId(),
                            databaseMethods.getUser(callbackQuery.getMessage().getChatId()).getMaiGroup()
                    )
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendHelpMessage(Message message)
    {
        try
        {
            telegram().execute(userErrorMessagesInterface.sendCommandsMessage(message.getChatId(), message.getFrom().getUserName()));
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAdminErrorMessage(Message message)
    {
        try
        {
            telegram().execute(adminErrorMessagesInterface.userIsNotAdmin(message.getChatId()));
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendScheduleMessage(Message message)
    {
        try
        {
            telegram().execute(userTemplateMessagesInterface.
                    sendScheduleMessage(message.getChatId(), databaseMethods.getUser(message.getChatId()).getMaiGroup()));
        }
        catch (TelegramApiException e)
        {

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

    @Override
    @Transactional
    public boolean isAdmin(long chatId) {
        Admin admin = databaseMethods.getAdmin(chatId);
        return admin != null;
    }

    @Transactional
    protected void registerUser(CallbackQuery callbackQuery) {
        MaiGroup maiGroup = new MaiGroup(callbackQuery.getData());
        User user = new User(callbackQuery.getFrom().getId(), maiGroup);
        databaseMethods.setUser(user);
    }

    @Transactional
    protected List<Subject> getUserSubjects(CallbackQuery callbackQuery) {
        User user = databaseMethods.getUser(callbackQuery.getFrom().getId());
        return user.getMaiGroup().getSubjects();
    }


    protected TelegramClient telegram() {
        return client.getTelegramClient();
    }

}
