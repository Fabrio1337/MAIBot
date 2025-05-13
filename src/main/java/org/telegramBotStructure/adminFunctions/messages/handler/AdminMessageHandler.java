package org.telegramBotStructure.adminFunctions.messages.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegramBotStructure.adminFunctions.adminState.AdminState;
import org.telegramBotStructure.adminFunctions.adminState.AdminStateHandler;
import org.telegramBotStructure.adminFunctions.adminState.AdminStateHandlerInterface;
import org.telegramBotStructure.adminFunctions.buttons.AdminButtonInterface;
import org.telegramBotStructure.adminFunctions.messages.templates.errorMessages.ErrorMessagesInterface;
import org.telegramBotStructure.adminFunctions.messages.templates.executedMessages.AdminExecutedMessagesInterface;
import org.telegramBotStructure.entity.*;
import org.telegramBotStructure.userFunctions.messages.handler.UserMessageHandler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminMessageHandler implements AdminMessageHandlerInterface {

    private final UserMessageHandler userMessageHandler;

    private final AdminExecutedMessagesInterface adminExecutedMessagesInterface;

    private final ErrorMessagesInterface errorMessagesInterface;

    private final AdminButtonInterface adminButtonInterface;

    private final AdminStateHandlerInterface adminStateHandlerInterface;


    @Override
    public void callback(CallbackQuery callbackQuery) {
        if(callbackQuery.getData().equals("Расписание") || callbackQuery.getData().equals("ДЗ") || callbackQuery.getData().equals("Информация"))
        {
            adminChoice(callbackQuery, callbackQuery.getData());
        }
        else if(callbackQuery.getData().startsWith("Расписание_"))
        {
            adminScheduleHandler(callbackQuery);
        }
        else if(callbackQuery.getData().startsWith("ДЗ_"))
        {
            adminHomeworkHandler(callbackQuery);
        }
        else if(callbackQuery.getData().startsWith("Информация_"))
        {
            adminMailingHandler(callbackQuery);
        }
        else if(callbackQuery.getData().startsWith("Добавить_"))
        {
            dayHandler(callbackQuery);
        }
        else if(callbackQuery.getData().startsWith("Удалить_"))
        {
            deleteSchedule(callbackQuery);
        }
        else if(callbackQuery.getData().equals("back_to_schedule"))
        {
            adminChoice(callbackQuery, "Расписание");
        }
        else{
            userMessageHandler.callback(callbackQuery);
        }


    }

    @Override
    @Transactional
    public void message(Message message) {
        AdminState currentState = adminStateHandlerInterface.getAdminState(message.getChatId());

        switch (currentState) {
            case WAITING_SCHEDULE_ADD:
                daysHandler(message);
                break;


            case WAITING_SUBJECT_ADD:

                break;

            case WAITING_HOMEWORK_ADD:

                break;

            case WAITING_INFORMATION_ADD:

                break;

            default:
                break;
        }
        if (userMessageHandler.getAdminWordsInterface().allWords().contains(message.getText())) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            if (userMessageHandler.getAdminWordsInterface().startAdminPanelWords().contains(message.getText()))
            {
                adminCommands(message);
            }
            else if (message.getText().matches("^/removeSubject\\s+(.+)$"))
            {
                removeSubjects(message);
            }
            else if(message.getText().matches("^/addHomework\\s+'([^']+)'\\s+'([^']+)'$"))
            {
                addHomework(message);
            }
            else if(message.getText().matches("^/addMailing\\s+(.+)$"))
            {
                addMailing(message);
            }
            else if(message.getText().matches("^/removeUser\\s+(.+)$"))
            {
                removeUserFromGroup(message);
            }
        } else {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            userMessageHandler.message(message);
        }
    }

    @Override
    public void daysHandler(Message message) {
        AdminState state = adminStateHandlerInterface.getAdminState(message.getChatId());
        String[] lines = message.getText().split("\\r?\\n");
        int count = 0;
        Weekday weekday = userMessageHandler.getDatabaseMethods().getWeekday(state.getDay());
        MaiGroup maiGroup = userMessageHandler.getDatabaseMethods().getUser(message.getChatId()).getMaiGroup();
        boolean addSchedule = true;
        for(String line : lines)
        {
            String content = line.replaceFirst("^\\d+\\.\\s*", "");

            count++;
            if (content.isBlank()) {
                continue;
            }

            String[] parts = content.split(",");

            if(parts.length == 1)
            {
                addSchedule = false;
            }
        }
        if(addSchedule)
            for (String line : lines) {
                String content = line.replaceFirst("^\\d+\\.\\s*", "");

                count++;
                if (content.isBlank()) {
                    continue;
                }

                String[] parts = content.split(",");
                if (parts.length == 3) {
                    String subjectName = parts[0].trim();
                    String room = parts[1].trim();
                    int weekType = Integer.parseInt(parts[2].trim());

                    Subject subject = new Subject(subjectName);

                    subject.addGroup(maiGroup);

                    Schedule schedule = new Schedule(weekday,  Integer.parseInt(room), (short) weekType, count);
                    subject.addSchedule(schedule);
                    schedule.setSubject(subject);
                    schedule.setMaiGroup(maiGroup);
                    maiGroup.addSchedule(schedule);
                    userMessageHandler.getDatabaseMethods().setSchedule(schedule);
                    userMessageHandler.getDatabaseMethods().setSubject(subject);
                }
        }
        else
        {
            try
            {
                userMessageHandler.getClient().getTelegramClient().execute(
                        errorMessagesInterface.sendErrorScheduleMessage(message.getChatId(), state.getDay())
                );
            }
            catch (TelegramApiException e)
            {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteSchedule(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        userMessageHandler.getDatabaseMethods().removeSchedule(
                parts[1],
                userMessageHandler.getDatabaseMethods()
                        .getUser(
                        callbackQuery
                                .getFrom()
                                .getId())
                        .getMaiGroup()
                        .getGroup()
        );
    }

    @Override
    public void adminChoice(CallbackQuery callbackQuery, String text)
    {
        adminStateHandlerInterface.setAdminState(callbackQuery.getMessage().getChatId(), AdminState.DEFAULT);
        try
        {
            userMessageHandler.getClient().getTelegramClient().execute(
                    adminButtonInterface.setAdminChoiceMessage(
                            callbackQuery.getFrom().getId(),
                            callbackQuery.getMessage().getMessageId(),
                            callbackQuery.getData()
                    )
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void adminScheduleHandler(CallbackQuery callbackQuery)
    {

        try {
            userMessageHandler.getClient().getTelegramClient().execute(
                    SendMessage.builder().chatId(callbackQuery.getFrom().getId()).text("в методе adminScheduleHandler").build(

                    ));
            String[] parts = callbackQuery.getData().split("_");
            if(parts[1].equals("Добавить"))
            {
                userMessageHandler.getClient().getTelegramClient().execute(
                        adminButtonInterface.setScheduleDays(
                                callbackQuery.getFrom().getId(),
                                callbackQuery.getMessage().getMessageId(),
                                parts[1]
                        )
                );
            }
            else if(parts[1].equals("Удалить"))
            {
                userMessageHandler.getClient().getTelegramClient().execute(
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
    public void dayHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        AdminState state = AdminState.WAITING_SCHEDULE_ADD;
        state.setDay(parts[1]);
        adminStateHandlerInterface.setAdminState(callbackQuery.getFrom().getId(),state);
        try
        {
            userMessageHandler.getClient().getTelegramClient().execute(
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
    public void adminHomeworkHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        if(parts[1].equals("Добавить"))
        {

        }
        else if(parts[1].equals("Удалить"))
        {

        }
        else if(parts[1].equals("Посмотреть"))
        {
            userMessageHandler.setSubjects(callbackQuery);
        }
    }

    @Override
    public void adminMailingHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        if(parts[1].equals("Добавить"))
        {

        }
        else if(parts[1].equals("Удалить"))
        {

        }
        else if(parts[1].equals("Посмотреть"))
        {
            userMessageHandler.sendMailing(callbackQuery);
        }
    }


    @Override
    public void adminCommands(Message message) {
        try
        {
            userMessageHandler.getClient().getTelegramClient().execute(
                    adminExecutedMessagesInterface.sendCommandsMessage(message.getChatId(), message.getFrom().getUserName())
            );
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSubjects(Message message) {
        boolean subjectFound = true;
        Pattern pattern = Pattern.compile("^/removeSubject\\s+(.+)$");
        Matcher matcher = pattern.matcher(message.getText());
        String subject = matcher.group(1);
        Subject subjectToGroup = new Subject(subject);
        User user = userMessageHandler.getDatabaseMethods().getUser(message.getChatId());
        try
        {

            for(Subject subject1 : user.getMaiGroup().getSubjects())
            {
                if(subjectToGroup.getSubjectName().equals(subject1.getSubjectName()))
                {
                    subjectToGroup.removeGroup(user.getMaiGroup());
                    user.getMaiGroup().removeSubjectFromGroup(subjectToGroup);
                    userMessageHandler.getDatabaseMethods().removeSubject(subjectToGroup);
                    userMessageHandler.getClient().getTelegramClient().execute(
                        adminExecutedMessagesInterface.sendSuccessDeleteSubject(message.getChatId())
                    );
                    break;
                }
                else
                {
                    subjectFound = false;
                }
            }
            if(!subjectFound)
            {
                userMessageHandler.getClient().getTelegramClient().execute(
                        errorMessagesInterface.errorDeleteubjectToGroup(message.getChatId())
                );
            }
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addHomework(Message message) {
        Pattern pattern = Pattern.compile("^/addHomework\\s+'([^']+)'\\s+'([^']+)'$");
        Matcher matcher = pattern.matcher(message.getText());
        String subject = matcher.group(1);
        String homework = matcher.group(2);
        Subject subjectGroup = userMessageHandler.getDatabaseMethods().getSubject(subject);
        User user = userMessageHandler.getDatabaseMethods().getUser(message.getChatId());
        try {
            for(Subject subject1 : user.getMaiGroup().getSubjects())
            {
                if(subject1.getSubjectName().equals(subjectGroup.getSubjectName()))
                {
                    subjectGroup = subject1;
                    break;
                }
                else {
                    userMessageHandler.getClient().getTelegramClient().execute(
                    errorMessagesInterface.errorAddHomeworkToGroup(message.getChatId())
                    );
                    subjectGroup = null;
                }
            }
            if(subjectGroup != null)
            {
                Homework homeworkGroup = new Homework(homework, subjectGroup);
                userMessageHandler.getDatabaseMethods().setHomework(homeworkGroup);
                userMessageHandler.getClient().getTelegramClient().execute(
                        adminExecutedMessagesInterface.sendSuccessHomework(message.getChatId())
                );
                for (User user1 : user.getMaiGroup().getUsers()) {
                    userMessageHandler.getClient().getTelegramClient().execute(
                            adminExecutedMessagesInterface.sendHomeworkMailingToAllUsersInGroup(user1.getUserId(), subject)
                    );
                }
            }


        }
        catch (NullPointerException | TelegramApiException e) {
            if (e instanceof NullPointerException) {
                try {
                    userMessageHandler.getClient().getTelegramClient().execute(
                            errorMessagesInterface.errorAddHomeworkToGroup(message.getChatId())
                    );
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace(); // Лог остальных ошибок
            }
        }

    }


    @Override
    public void addMailing(Message message) {
        Pattern pattern = Pattern.compile("^/addMailing\\s+(.+)$");
        Matcher matcher = pattern.matcher(message.getText());
        String mailing = matcher.group(1);
        Mailing mailingToGroup = new Mailing(mailing);
        mailingToGroup.setMaiGroup(
                userMessageHandler.getDatabaseMethods().getUser(message.getChatId()).getMaiGroup()
        );
        try {
            userMessageHandler.getDatabaseMethods().setMailing(mailingToGroup);
            userMessageHandler.getClient().getTelegramClient().execute(
                    adminExecutedMessagesInterface.sendSuccessMailing(message.getChatId())
            );
            List<User> users = mailingToGroup.getMaiGroup().getUsers();
            for (User user : users) {
                userMessageHandler.getClient().getTelegramClient().execute(
                        adminExecutedMessagesInterface.sendMailingToAllUsersInGroup(user.getUserId(), mailing)
                );
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserFromGroup(Message message) {
        Pattern pattern = Pattern.compile("^/removeUser\\s+(.+)$");
        Matcher matcher = pattern.matcher(message.getText());
        String user = matcher.group(1);
        Admin admin = userMessageHandler.getDatabaseMethods().getAdmin(message.getChatId());
       try {
           if(admin.is_set_admins())
           {
               try {
                   userMessageHandler.getDatabaseMethods().removeUser(Long.parseLong(user));
                   userMessageHandler.getClient().getTelegramClient().execute(
                           adminExecutedMessagesInterface.sendSuccessDeleteUser(message.getChatId())
                   );
               }
               catch ( NumberFormatException e ) {
                   userMessageHandler.getClient().getTelegramClient().execute(
                           errorMessagesInterface.sendErrorNumberFormatMessage(message.getChatId())
                   );
               }
           }
           else
           {
               User userIsAdmin = userMessageHandler.getDatabaseMethods().getUser(admin.getUserId());

               try {
                   User maybeDeletedUser = userMessageHandler.getDatabaseMethods().getUser(Long.parseLong(user));
                   if(userIsAdmin.getMaiGroup().getGroup().equals(maybeDeletedUser.getMaiGroup().getGroup()))
                   {
                       userMessageHandler.getDatabaseMethods().removeUser(Long.parseLong(user));
                       userMessageHandler.getClient().getTelegramClient().execute(
                               adminExecutedMessagesInterface.sendSuccessDeleteUser(message.getChatId())
                       );
                   }
                   else
                   {
                       userMessageHandler.getClient().getTelegramClient().execute(
                               errorMessagesInterface.errorUserNotInYourGroupMessage(message.getChatId())
                       );
                   }
               }
               catch ( NumberFormatException e ) {
                    userMessageHandler.getClient().getTelegramClient().execute(
                            errorMessagesInterface.sendErrorNumberFormatMessage(message.getChatId())
                    );
               }
           }
       }
       catch (TelegramApiException e)
       {
           e.printStackTrace();
       }

    }
}
