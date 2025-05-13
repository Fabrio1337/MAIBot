package org.telegramBotStructure.adminFunctions.buttons;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;

import java.util.ArrayList;
import java.util.List;


@Getter
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminButtons implements AdminButtonInterface {

    private final UserButtonsInterface userButtons;

    @Override
    public InlineKeyboardMarkup scheduleDays(String text)
    {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        InlineKeyboardRow row = new InlineKeyboardRow();

        String[] choices = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        for (int i = 0; i < choices.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(choices[i])
                    .callbackData(text + "_" +choices[i])
                    .build();


            row.add(button);

            if (row.size() == 2 || i == choices.length - 1) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }

        row = new InlineKeyboardRow();
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("back_to_schedule")
                .build();
        row.add(backButton);
        rows.add(row);

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    @Override
    public EditMessageReplyMarkup setScheduleDays(long chatId, int messageId, String text)
    {
        return EditMessageReplyMarkup.builder()
                .chatId(chatId).messageId(messageId)
                .replyMarkup(scheduleDays(text))
                .build();
    }

    @Override
    public InlineKeyboardMarkup adminChoice(String text)
    {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        InlineKeyboardRow row = new InlineKeyboardRow();

        String[] choices = {"Добавить", "Удалить", "Посмотреть"};
        for (int i = 0; i < choices.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(choices[i])
                    .callbackData(text + "_" + choices[i])
                    .build();


            row.add(button);

            if (row.size() == 2 || i == choices.length - 1) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }

        row = new InlineKeyboardRow();
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("back_to_menu")
                .build();
        row.add(backButton);
        rows.add(row);

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    @Override
    public EditMessageReplyMarkup setAdminChoiceMessage(long chatId, int messageId,String text)
    {
        return EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(adminChoice(text))
                .build();
    }

}
