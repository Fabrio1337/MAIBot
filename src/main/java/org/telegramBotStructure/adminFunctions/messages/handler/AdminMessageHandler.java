package org.telegramBotStructure.adminFunctions.messages.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegramBotStructure.adminFunctions.databaseActions.AdminDatabaseAction;
import org.telegramBotStructure.adminFunctions.adminState.AdminState;
import org.telegramBotStructure.adminFunctions.adminState.AdminStateHandlerInterface;
import org.telegramBotStructure.adminFunctions.messages.handler.sendMessages.SendMessageToAdmin;
import org.telegramBotStructure.adminFunctions.words.AdminWordsInterface;
import org.telegramBotStructure.entity.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminMessageHandler implements AdminMessageHandlerInterface {

    private final AdminWordsInterface adminWordsInterface;

    private final AdminStateHandlerInterface adminStateHandlerInterface;

    private final AdminDatabaseAction adminDatabaseAction;

    private final SendMessageToAdmin sendMessageToAdmin;


    @Override
    @Transactional
    public void callback(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("Расписание") || callbackQuery.getData().equals("ДЗ") || callbackQuery.getData().equals("Информация")) {
            adminChoice(callbackQuery, callbackQuery.getData());
        } else if (callbackQuery.getData().startsWith("Расписание_")) {
            adminScheduleHandler(callbackQuery);
        } else if (callbackQuery.getData().startsWith("ДЗ_")) {
            adminHomeworkHandler(callbackQuery);
        } else if (callbackQuery.getData().startsWith("Информация_")) {
            adminMailingHandler(callbackQuery);
        } else if (callbackQuery.getData().startsWith("Добавить_")) {
            dayHandler(callbackQuery);
        } else if (callbackQuery.getData().startsWith("Удалить_")) {
            deleteSchedule(callbackQuery);
        } else if (callbackQuery.getData().startsWith("УдалитьДЗ_")) {
            //отображение кнопки
        } else if (callbackQuery.getData().startsWith("ДобавитьДЗ_")) {
            addingHomeworkHandler(callbackQuery, null);
        } else if (callbackQuery.getData().equals("back_to_schedule")) {
            adminChoice(callbackQuery, "Расписание");
        } else {
            sendMessageToAdmin.callback(callbackQuery);
        }


    }

    @Override
    @Transactional
    public void message(Message message) {
        AdminState currentState = adminStateHandlerInterface.getAdminState(message.getChatId());

        switch (currentState) {
            case WAITING_SCHEDULE_ADD:
                daysHandler(message);
                adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
                return;


            case WAITING_SUBJECT_ADD:
                adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
                return;

            case WAITING_HOMEWORK_ADD:
                addingHomeworkHandler(null, message);
                adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
                return;

            case WAITING_INFORMATION_ADD:
                addMailingToGroup(message);
                adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
                return;

            default:
                break;
        }

        if (adminWordsInterface.startAdminPanelWords().contains(message.getText().toLowerCase())) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            adminCommands(message);
        } else if (message.getText().toLowerCase().startsWith("/removesubject ")) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            removeSubjects(message);
        } else if (message.getText().toLowerCase().startsWith("/addhomework ")) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            addHomework(message);
        } else if (message.getText().toLowerCase().startsWith("/addmailing ")) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            addMailing(message);
        } else if (message.getText().toLowerCase().startsWith("/removeuser ")) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            removeUserFromGroup(message);
        } else if (!adminWordsInterface.allWords().contains(message.getText().toLowerCase())) {
            adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.DEFAULT);
            sendMessageToAdmin.message(message);
        }
    }

    @Override
    public void daysHandler(Message message) {
        AdminState state = adminStateHandlerInterface.getAdminState(message.getChatId());
        adminStateHandlerInterface.setAdminState(message.getChatId(), AdminState.WAITING_SCHEDULE_ADD);
        String[] lines = message.getText().split("\\r?\\n");
        Weekday weekday = adminDatabaseAction.getCurrentWeekday(state);
        MaiGroup maiGroup = adminDatabaseAction.getCurrentUser(message.getChatId()).getMaiGroup();
        int count = 0;
        boolean addSchedule = true;
        boolean isAdded = false;
        if (lines.length > 7) {
            sendMessageToAdmin.daysHandler(message, state);
        } else {
            for (String line : lines) {
                String content = line.replaceFirst("^\\d+\\.\\s*", "");

                count++;
                if (content.isBlank()) {
                    continue;
                }

                String[] parts = content.split(",");

                if (parts.length == 1) {
                    addSchedule = false;
                }
            }
            count = 0;
            if (addSchedule) {
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

                        Schedule schedule = new Schedule(weekday, Integer.parseInt(room), (short) weekType, count);
                        Subject subject1 = adminDatabaseAction.getSubjectByName(subjectName);
                        if (subject1 == null) {
                            subject1 = new Subject(subjectName);
                        }
                        adminDatabaseAction.setSchedule(schedule, maiGroup, subject);
                    }
                }
                sendMessageToAdmin.successAddSchedule(message, weekday.getDay());
                sendMessageToAdmin.sendStartMessage(message);
            } else {
                sendMessageToAdmin.daysHandler(message, state);
            }
        }

    }

    @Override
    public void addingHomeworkHandler(CallbackQuery callbackQuery, Message message)
    {
        sendMessageToAdmin.adminHomeworkHandler(callbackQuery, message);
    }


    @Override
    public void addMailingToGroup(Message message)
    {
        String mailing = message.getText();
        Mailing mail = new Mailing(mailing);
        MaiGroup maiGroup = adminDatabaseAction.getCurrentUser(message.getChatId()).getMaiGroup();
        mail.setMaiGroup(maiGroup);
        adminDatabaseAction.setMailing(mail);

        for (User user : maiGroup.getUsers())
        {
            sendMessageToAdmin.sendMailingToAllUsersInGroup(user, mailing);
        }
    }

    @Override
    public void deleteSchedule(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");

        User user = adminDatabaseAction.getCurrentUser(callbackQuery.getFrom().getId());

        List<Schedule> allSchedules = adminDatabaseAction.getCurrentSchedule(user.getMaiGroup().getGroup());

       boolean isDeleted =  adminDatabaseAction.removeSchedule(allSchedules, parts[1], user.getUserId());

       if(isDeleted)
       {
            sendMessageToAdmin.successDeleteSchedule((Message) callbackQuery.getMessage(), parts[1]);
       }
       else
       {
           sendMessageToAdmin.errorDeleteSchedule((Message) callbackQuery.getMessage(), parts[1]);
       }
    }

    @Override
    public void adminChoice(CallbackQuery callbackQuery, String text)
    {
        adminStateHandlerInterface.setAdminState(callbackQuery.getMessage().getChatId(), AdminState.DEFAULT);
        sendMessageToAdmin.adminChoise(callbackQuery, text);
    }

    @Override
    public void adminScheduleHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");

        sendMessageToAdmin.adminScheduleHandler(callbackQuery, parts);
    }

    @Override
    public void dayHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        AdminState state = AdminState.WAITING_SCHEDULE_ADD;
        state.setDay(parts[1]);
        adminStateHandlerInterface.setAdminState(callbackQuery.getFrom().getId(),state);
        sendMessageToAdmin.dayHandler(callbackQuery, parts);
    }

    @Override
    public void adminHomeworkHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        sendMessageToAdmin.adminHomeworkHandler(callbackQuery, parts);
    }

    @Override
    public void adminMailingHandler(CallbackQuery callbackQuery)
    {
        String[] parts = callbackQuery.getData().split("_");
        sendMessageToAdmin.adminMailingHandler(callbackQuery, parts);
    }


    @Override
    public void adminCommands(Message message) {
        sendMessageToAdmin.adminCommands(message);
    }

    @Override
    public void removeSubjects(Message message) {
        boolean subjectFound = true;
        Pattern pattern = Pattern.compile("^/removeSubject\\s+(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message.getText());
        String subject = matcher.group(1);

        Subject subjectToGroup = new Subject(subject);
        User user = adminDatabaseAction.getCurrentUser(message.getChatId());

        for(Subject subject1 : user.getMaiGroup().getSubjects())
        {
            if(subjectToGroup.getSubjectName().equals(subject1.getSubjectName()))
            {
                adminDatabaseAction.removeSubject(subjectToGroup, user);
                sendMessageToAdmin.successRemoveSubjects(message);
                break;
            }
            else
            {
                subjectFound = false;
            }
        }
        if(!subjectFound)
        {
            sendMessageToAdmin.errorRemoveSubjects(message);
        }

    }

    @Override
    public void addHomework(Message message) throws NullPointerException{
        Pattern pattern = Pattern.compile("^/addHomework\\s+'([^']+)'\\s+'([^']+)'$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message.getText());
        String subject = matcher.group(1);
        String homework = matcher.group(2);
        User user = adminDatabaseAction.getCurrentUser(message.getChatId());
        Subject subjectGroup = adminDatabaseAction.getSubject(subject, user.getMaiGroup().getGroup());
        try {
            for(Subject subject1 : user.getMaiGroup().getSubjects())
            {
                if(subject1.getSubjectName().equals(subjectGroup.getSubjectName()))
                {
                    subjectGroup = subject1;
                    break;
                }
                else {
                    sendMessageToAdmin.errorAddHomework(message);
                    subjectGroup = null;
                }
            }
            if(subjectGroup != null)
            {
                Homework homeworkGroup = new Homework(homework, subjectGroup);
                adminDatabaseAction.setHomework(homeworkGroup);
                sendMessageToAdmin.sendSuccessAddHomework(message);
                for (User user1 : user.getMaiGroup().getUsers()) {
                    sendMessageToAdmin.sendMailingAllUsersToGroupAboutHomeworkAdded(user1, subject);
                }
            }
        }
        catch (NullPointerException e) {
            sendMessageToAdmin.errorAddHomework(message);
        }

    }


    @Override
    public void addMailing(Message message) {
        Pattern pattern = Pattern.compile("^/addMailing\\s+(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message.getText());
        String mailing = matcher.group(1);
        Mailing mailingToGroup = new Mailing(mailing);
        mailingToGroup.setMaiGroup(
                adminDatabaseAction.getCurrentGroup(message.getChatId())
        );
        adminDatabaseAction.setMailing(mailingToGroup);
        sendMessageToAdmin.sendSuccessMailing(message);
        List<User> users = mailingToGroup.getMaiGroup().getUsers();
        for (User user : users) {
            sendMessageToAdmin.sendMailingToAllUsersInGroup(user, mailing);
        }
    }


    @Override
    public void removeUserFromGroup(Message message) {
        Pattern pattern = Pattern.compile("^/removeUser\\s+(\\d+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message.getText());

        if (!matcher.matches()) {
            sendMessageToAdmin.sendErrorNumberFormatMessage(message);
            return;
        }

        String user = matcher.group(1);
        long userId;
        try {
            userId = Long.parseLong(user);
        } catch (NumberFormatException e) {
            sendMessageToAdmin.sendErrorNumberFormatMessage(message);
            return;
        }

        Admin admin = adminDatabaseAction.getUserIsAdmin(message.getChatId());

        if (admin.is_set_admins()) {
            boolean isSuccess = adminDatabaseAction.removeUser(userId);
            if(isSuccess) sendMessageToAdmin.sendSuccessDeleteUserNotYourGroup(message);
            else sendMessageToAdmin.errorDeleteUser(message);
        } else {
            User userIsAdmin = adminDatabaseAction.getCurrentUser(admin.getUserId());
            User maybeDeletedUser = adminDatabaseAction.getCurrentUser(userId);

            if (userIsAdmin.getMaiGroup().getGroup().equals(maybeDeletedUser.getMaiGroup().getGroup())) {
                boolean isSuccess = adminDatabaseAction.removeUser(userId);
                if(isSuccess) sendMessageToAdmin.sendSuccessDeleteUser(message);
                else sendMessageToAdmin.errorDeleteUser(message);
            } else {
                sendMessageToAdmin.errorUserNotInYourGroupMessage(message);
            }
        }
    }

}
