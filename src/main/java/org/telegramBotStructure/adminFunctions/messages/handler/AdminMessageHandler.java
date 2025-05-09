package org.telegramBotStructure.adminFunctions.messages.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegramBotStructure.adminFunctions.messages.templates.executedMessages.AdminExecutedMessagesInterface;
import org.telegramBotStructure.userFunctions.messages.handler.UserMessageHandler;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminMessageHandler implements AdminMessageHandlerInterface {

    private final UserMessageHandler userMessageHandler;

    private final AdminExecutedMessagesInterface adminExecutedMessagesInterface;

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
    public void message(Message message) {
        if (userMessageHandler.getAdminWordsInterface().allWords().contains(message.getText())) {
            if (userMessageHandler.getAdminWordsInterface().startAdminPanelWords().contains(message.getText()))
            {
                adminExecutedMessagesInterface.sendCommandsMessage(message.getChatId(), message.getFrom().getUserName());
            }
            else if(userMessageHandler.getAdminWordsInterface().addSubjectsWords().contains(message.getText()))
            {

            }
            else if (userMessageHandler.getAdminWordsInterface().removeSubjectsWords().contains(message.getText()))
            {

            }
            else if(userMessageHandler.getAdminWordsInterface().addHomeworksWords().contains(message.getText()))
            {

            }
            else if(userMessageHandler.getAdminWordsInterface().addMailingWords().contains(message.getText()))
            {

            }
            else if(userMessageHandler.getAdminWordsInterface().addScheduleWords().contains(message.getText()))
            {

            }
            else if(userMessageHandler.getAdminWordsInterface().removeScheduleWords().contains(message.getText()))
            {

            }
            else if(userMessageHandler.getAdminWordsInterface().RemoveUserInGroupWords().contains(message.getText()))
            {

            }
        } else {

            userMessageHandler.message(message);
        }
    }


    @Override
    @Transactional
    public SendMessage adminCommands() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addSubjects() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeSubjects() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addHomework() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeHomework() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addMailing() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeMailing() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage addTimetable() {
        return null;
    }

    @Override
    @Transactional
    public SendMessage removeTimetable() {
        return null;
    }
}
