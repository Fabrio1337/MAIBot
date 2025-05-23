package org.telegramBotStructure.bot.status;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.adminFunctions.messages.handler.AdminMessageHandlerInterface;
import org.telegramBotStructure.entity.Admin;
import org.telegramBotStructure.userFunctions.messages.handler.UserMessageHandlerInterface;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CheckingUserOrAdmin implements CheckUserOrAdmin{

    private final DatabaseMethods databaseMethods;

    private final AdminMessageHandlerInterface adminMessageHandlerInterface;

    private final UserMessageHandlerInterface userMessageHandlerInterface;

    @Override
    @Transactional
    public void getUpdateMessage(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                if (isAdmin(update.getMessage().getChatId())) {
                    adminMessageHandlerInterface.message(update.getMessage());
                } else {
                    userMessageHandlerInterface.message(update.getMessage());
                }
            } else if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                System.out.println(callbackQuery.getData());
                if (isAdmin(callbackQuery.getFrom().getId())) {
                    adminMessageHandlerInterface.callback(callbackQuery);
                } else {
                    userMessageHandlerInterface.callback(callbackQuery);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    @Transactional
    public boolean isAdmin(long chatId) {
        Admin admin = databaseMethods.getAdmin(chatId);
        return admin != null;
    }
}
