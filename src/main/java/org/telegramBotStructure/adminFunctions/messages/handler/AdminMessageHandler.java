package org.telegramBotStructure.adminFunctions.messages.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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

    @Override
    public void callback(CallbackQuery callbackQuery) {
        if(true)
        {

        }
        else{
            userMessageHandler.callback(callbackQuery);
        }
    }

    @Override
    @Transactional
    public void message(Message message) {
        if (userMessageHandler.getAdminWordsInterface().allWords().contains(message.getText())) {
            if (userMessageHandler.getAdminWordsInterface().startAdminPanelWords().contains(message.getText()))
            {
                adminCommands(message);
            }
            else if(message.getText().matches("^/addSubject\\s+(.+)$"))
            {
                addSubjects(message);
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

            userMessageHandler.message(message);
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
    public void addSubjects(Message message) {
        Pattern pattern = Pattern.compile("^/addsubject\\s+(.+)$");
        Matcher matcher = pattern.matcher(message.getText());
        String subject = matcher.group(1);
        Subject subjectToGroup = new Subject(subject);
        User user = userMessageHandler.getDatabaseMethods().getUser(message.getChatId());
        try {
            for(Subject subject1 : user.getMaiGroup().getSubjects())
            {
                if(subjectToGroup.getSubjectName().equals(subject1.getSubjectName()))
                {
                    userMessageHandler.getClient().getTelegramClient().execute(
                        errorMessagesInterface.errorAddSubjectToGroup(message.getChatId())
                    );
                }
                else
                {
                    subjectToGroup.addGroup(user.getMaiGroup());
                    userMessageHandler.getDatabaseMethods().setSubject(subjectToGroup);
                    userMessageHandler.getClient().getTelegramClient().execute(
                        adminExecutedMessagesInterface.sendSuccessSubject(message.getChatId())
                    );
                }
            }
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
