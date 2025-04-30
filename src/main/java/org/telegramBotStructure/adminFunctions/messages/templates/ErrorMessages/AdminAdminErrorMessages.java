package org.telegramBotStructure.adminFunctions.messages.templates.ErrorMessages;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class AdminAdminErrorMessages implements AdminErrorMessagesInterface {

    @Override
    public SendMessage userIsNotAdmin(long chatId) {
        String text = "❌Вы не являетесь старостой, обратитесь к старосте, если нужно добавить/изменить какую либо информацию❌";
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        return message;
    }
}
