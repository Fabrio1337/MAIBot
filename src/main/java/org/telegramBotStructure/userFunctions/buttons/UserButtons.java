package org.telegramBotStructure.userFunctions.buttons;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegramBotStructure.entity.Subject;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserButtons implements UserButtonsInterface{

    //если пользователя нет в Базе Данных, то ему нужно выбрать курс
    @Override
    public InlineKeyboardMarkup setWelcomeButtons() {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        String[] courses = {"1", "2", "3", "4", "5"};

        InlineKeyboardRow row = new InlineKeyboardRow();

        for (int i = 0; i < courses.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(courses[i])
                    .callbackData("course_" + courses[i])
                    .build();

            row.add(button);

            if (row.size() == 2 || i == courses.length - 1) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    //кнопки выбора групп для новых пользователей
    //группы: МСО-#01Б, МСО-#02Б, МСО-#05Б, МСО-#01C, МСО-#03C, МСО-#05C, МСО-#02C, МСО-#04C, МСО-#03Бк,
    @Override
    public InlineKeyboardMarkup setGroupsButtons(String course) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        String[] groups = {"МСО-#01Б", "МСО-#02Б", "МСО-#05Б",
                "МСО-#01C", "МСО-#03C", "МСО-#05C", "МСО-#02C", "МСО-#04C", "МСО-#03Бк"};
        InlineKeyboardRow row = new InlineKeyboardRow();

        for (int i = 0; i < groups.length; i++) {
            groups[i] = groups[i].replace("#", course);
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(groups[i])
                    .callbackData(groups[i])
                    .build();

            row.add(button);

            if (row.size() == 2 || i == groups.length - 1) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }
        row = new InlineKeyboardRow();
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("back_to_course")
                .build();
        row.add(backButton);
        rows.add(row);

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    //кнопки выбора предметов определенного курса
    @Override
    public InlineKeyboardMarkup setSubjectButtons(List<Subject> subjects)
    {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        InlineKeyboardRow row = new InlineKeyboardRow();
        for (int i = 0; i < subjects.size(); i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(subjects.get(i).getSubjectName())
                    .callbackData("subject_" + subjects.get(i).getSubjectName())
                    .build();

            row.add(button);

            if (row.size() == 2 || i == subjects.size() - 1) {
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

    //меню пользователя
    @Override
    public InlineKeyboardMarkup setUserChoiceButtons()
    {

        List<InlineKeyboardRow> rows = new ArrayList<>();
        InlineKeyboardRow row = new InlineKeyboardRow();
        String[] choices = {"Расписание", "ДЗ", "Информация"};
        for (int i = 0; i < choices.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(choices[i])
                    .callbackData(choices[i])
                    .build();


            row.add(button);

            if (row.size() == 2 || i == choices.length - 1) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    @Override
    public EditMessageReplyMarkup setGroupButtons(long chatId, int messageId, String callbackData) {

        int underscoreIndex = callbackData.indexOf("_");

        return EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(setGroupsButtons(callbackData.substring(underscoreIndex + 1)))
                .build();
    }

    @Override
    public EditMessageReplyMarkup returnCoursesButtons(long chatId, int messageId) {
        return EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(setWelcomeButtons())
                .build();
    }

    @Override
    public EditMessageReplyMarkup returnToMenuButtons(long chatId, int messageId) {
        return EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(setUserChoiceButtons())
                .build();
    }

}
