package org.telegramBotStructure.adminFunctions.messages.handler.sendMessages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegramBotStructure.adminFunctions.adminState.AdminState;
import org.telegramBotStructure.adminFunctions.adminState.AdminStateHandlerInterface;
import org.telegramBotStructure.adminFunctions.buttons.AdminButtonInterface;
import org.telegramBotStructure.adminFunctions.buttons.AdminButtons;
import org.telegramBotStructure.adminFunctions.databaseActions.AdminDatabaseActionImpl;
import org.telegramBotStructure.adminFunctions.messages.templates.errorMessages.ErrorMessagesInterface;
import org.telegramBotStructure.adminFunctions.messages.templates.executedMessages.AdminExecutedMessagesInterface;
import org.telegramBotStructure.entity.Homework;
import org.telegramBotStructure.entity.MaiGroup;
import org.telegramBotStructure.entity.Subject;
import org.telegramBotStructure.entity.User;
import org.telegramBotStructure.userFunctions.messages.handler.UserMessageHandler;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SendMessageToAdminImpl implements SendMessageToAdmin {

    private final UserMessageHandler userMessageHandler;

    private final AdminExecutedMessagesInterface adminExecutedMessagesInterface;

    private final ErrorMessagesInterface errorMessagesInterface;

    private final AdminButtonInterface adminButtonInterface;

    private final AdminStateHandlerInterface adminStateHandlerInterface;

    private final AdminDatabaseActionImpl adminDatabaseActionImpl;
    private final AdminButtons adminButtons;


    @Override
    public void callback(CallbackQuery callbackQuery)
    {
        userMessageHandler.callback(callbackQuery);
    }

    @Override
    public void message(Message message)
    {
        userMessageHandler.message(message);
    }

    @Override
    public void sendStartMessage(Message message)
    {
        userMessageHandler.sendStartMessage(message);
    }

    @Override
    public void daysHandler(Message message, AdminState state)
    {
        try
        {
            telegram().execute(
                    errorMessagesInterface.sendErrorScheduleMessage(message.getChatId(), state.getDay())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void adminChoise(CallbackQuery callbackQuery, String text)
    {
        try
        {
            telegram().execute(
                    adminButtonInterface.setAdminChoiceMessage(
                            callbackQuery.getFrom().getId(),
                            callbackQuery.getMessage().getMessageId(),
                            text
                    )
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void adminScheduleHandler(CallbackQuery callbackQuery, String[] parts)
    {
        try {
            if(parts[1].equals("Добавить"))
            {
                telegram().execute(
                        adminButtonInterface.setScheduleDays(
                                callbackQuery.getFrom().getId(),
                                callbackQuery.getMessage().getMessageId(),
                                parts[1]
                        )
                );
            }
            else if(parts[1].equals("Удалить"))
            {
                telegram().execute(
                        adminButtonInterface.setScheduleDays(
                                callbackQuery.getFrom().getId(),
                                callbackQuery.getMessage().getMessageId(),
                                parts[1]
                        )
                );
            }
            else if(parts[1].equals("Посмотреть"))
            {
                userMessageHandler.sendScheduleMessage((Message) callbackQuery.getMessage());
            }
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void dayHandler(CallbackQuery callbackQuery, String[] parts)
    {
        try
        {
            telegram().execute(
                    adminExecutedMessagesInterface.sendWaitingScheduleMessage
                            (
                                    callbackQuery.getFrom().getId(),
                                    parts[1]
                            )
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void adminHomeworkHandler(CallbackQuery callbackQuery, Message message)
    {
        if(callbackQuery != null && message == null) {
            String[] parts = callbackQuery.getData().split("_");
            AdminState state = adminStateHandlerInterface.getAdminState(message.getChatId());
            state.setSubject(parts[1]);
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.WAITING_HOMEWORK_ADD);
        }
        else if(message != null && callbackQuery == null) {
            String homeworkText = message.getText();
            AdminState state = adminStateHandlerInterface.getAdminState(message.getChatId());
            String subjectName = state.getSubject();

            User user = adminDatabaseActionImpl.getCurrentUser(message.getChatId());

            Subject subject = adminDatabaseActionImpl.getSubject(subjectName, user.getMaiGroup().getGroup());

            Homework homework = new Homework(homeworkText, subject);

            adminDatabaseActionImpl.setHomework(homework);

            //добавление рассылки
            for (User user1 : user.getMaiGroup().getUsers()) {
                sendMailingAllUsersToGroupAboutHomeworkAdded(user1, subject.getSubjectName());
            }
        }
    }

    @Override
    public void adminHomeworkHandler(CallbackQuery callbackQuery, String[] parts)
    {
        try {
            MaiGroup group = adminDatabaseActionImpl.getCurrentUser(callbackQuery.getFrom().getId()).getMaiGroup();
            Set<Subject> subjects = group.getSubjects();
            if(parts[1].equals("Добавить"))
            {
                if(subjects.isEmpty()) telegram().execute(
                        errorMessagesInterface.sendAddNullSubjectsMessage(callbackQuery.getFrom().getId())
                );
                else telegram().execute(
                        adminButtons.setSubjectButtons(
                                callbackQuery.getFrom().getId(),
                                callbackQuery.getMessage().getMessageId(),
                                subjects,
                                "ДобавитьДЗ"
                        )
                );
            }
            else if(parts[1].equals("Удалить"))
            {
                if(subjects.isEmpty()) telegram().execute(
                        errorMessagesInterface.sendRemoveNullSubjectsMessage(callbackQuery.getFrom().getId())
                );
                else telegram().execute(
                        adminButtons.setSubjectButtons(
                                callbackQuery.getFrom().getId(),
                                callbackQuery.getMessage().getMessageId(),
                                subjects,
                                "УдалитьДЗ"
                        )
                );
            }
            else if(parts[1].equals("Посмотреть"))
            {
                userMessageHandler.setSubjects(callbackQuery);
            }
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void adminMailingHandler(CallbackQuery callbackQuery, String[] parts)
    {
        if(parts[1].equals("Добавить"))
        {
            adminExecutedMessagesInterface.sendWaitingMailingMessage(callbackQuery.getFrom().getId());
            adminStateHandlerInterface.setAdminState(callbackQuery.getFrom().getId(), AdminState.WAITING_INFORMATION_ADD);

        }
        else if(parts[1].equals("Удалить"))
        {
            User user = adminDatabaseActionImpl.getCurrentUser(callbackQuery.getFrom().getId());
            adminDatabaseActionImpl.removeMailing(user);
        }
        else if(parts[1].equals("Посмотреть"))
        {
            userMessageHandler.sendMailing(callbackQuery);
        }
    }

    @Override
    public void adminCommands(Message message)
    {
        try
        {
            telegram().execute(
                    adminExecutedMessagesInterface.sendCommandsMessage(message.getChatId(), message.getFrom().getUserName())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void successRemoveSubjects(Message message)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessDeleteSubject(message.getChatId())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void errorRemoveSubjects(Message message)
    {
        try {
            telegram().execute(
                    errorMessagesInterface.errorDeleteubjectToGroup(message.getChatId())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void errorAddHomework(Message message)
    {
        try
        {
            telegram().execute(
                    errorMessagesInterface.errorAddHomeworkToGroup(message.getChatId())
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSuccessAddHomework(Message message)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessHomework(message.getChatId())
            );
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailingAllUsersToGroupAboutHomeworkAdded(User user, String subject)
    {
        try
        {
            telegram().execute(
                    adminExecutedMessagesInterface.sendHomeworkMailingToAllUsersInGroup(user.getUserId(), subject)
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSuccessMailing(Message message)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessMailing(message.getChatId())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailingToAllUsersInGroup(User user, String mailing)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendMailingToAllUsersInGroup(user.getUserId(), mailing)
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSuccessDeleteUser(Message message)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessDeleteUser(message.getChatId())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendErrorNumberFormatMessage(Message message)
    {
        try {
            telegram().execute(
                    errorMessagesInterface.sendErrorNumberFormatMessage(message.getChatId())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void successDeleteSchedule(Message message, String day)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessDeleteSchedule(message.getChatId(), day)
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void successAddSchedule(Message message, String day)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessAddSchedule(message.getChatId(), day)
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void errorDeleteSchedule(Message message, String day)
    {
        try {
            telegram().execute(
                    errorMessagesInterface.errorDeleteSchedule(message.getChatId(), day)
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void errorUserNotInYourGroupMessage(Message message)
    {
        try {
            telegram().execute(
                    errorMessagesInterface.errorUserNotInYourGroupMessage(message.getChatId())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void errorDeleteUser(Message message)
    {
        try {
            telegram().execute(
                    errorMessagesInterface.errorDeleteUser(message.getChatId())
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSuccessDeleteUserNotYourGroup(Message message)
    {
        try {
            telegram().execute(
                    adminExecutedMessagesInterface.sendSuccessDeleteUserNotYourGroup(message.getChatId())
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    protected TelegramClient telegram() {
        return userMessageHandler.getClient().getTelegramClient();
    }

}
