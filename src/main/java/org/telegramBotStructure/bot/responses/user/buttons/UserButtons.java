package org.telegramBotStructure.bot.responses.user.buttons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;
import org.telegramBotStructure.entity.Subject;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserButtons implements UserButtonsInterface{

    //если пользователя нет в Базе Данных, то ему нужно выбрать курс
    @Override
    public InlineKeyboardMarkup setWelcomeButtons() {
        InlineKeyboardMarkup welcomeKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        String[] courses = {"1", "2", "3", "4", "5"};

        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 0; i < courses.length; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(courses[i]);
            button.setCallbackData("course_" + courses[i]);

            row.add(button);

            if (row.size() == 2 || i == courses.length - 1) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }

        welcomeKeyboardMarkup.setKeyboard(rows);
        return welcomeKeyboardMarkup;
    }

    //кнопки выбора групп для новых пользователей
    //группы: МСО-#01Б, МСО-#02Б, МСО-#05Б, МСО-#01C, МСО-#03C, МСО-#05C, МСО-#02C, МСО-#04C, МСО-#03Бк,
    @Override
    public InlineKeyboardMarkup setGroupsButtons(String course) {
        InlineKeyboardMarkup groupsKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        String[] groups = {"МСО-#01Б", "МСО-#02Б", "МСО-#05Б",
                "МСО-#01C", "МСО-#03C", "МСО-#05C", "МСО-#02C", "МСО-#04C", "МСО-#03Бк"};
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 0; i < groups.length; i++) {
            groups[i] = groups[i].replace("#", course);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(groups[i]);
            button.setCallbackData("group_" + groups[i]);

            row.add(button);

            if (row.size() == 2 || i == groups.length - 1) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        groupsKeyboardMarkup.setKeyboard(rows);
        return groupsKeyboardMarkup;
    }

    public InlineKeyboardMarkup setSubjectButtons(List<Subject> subjects)
    {
        InlineKeyboardMarkup subjectsKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < subjects.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(subjects.get(i).getSubjectName());
            button.setCallbackData("subject_" + subjects.get(i).getSubjectName());

            row.add(button);

            if (row.size() == 2 || i == subjects.size() - 1) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        subjectsKeyboardMarkup.setKeyboard(rows);
        return subjectsKeyboardMarkup;
    }

    public InlineKeyboardMarkup setUserChoiceButtons()
    {
        InlineKeyboardMarkup choicesKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        String[] choices = {"Расписание", "ДЗ", "Информация"};
        for (int i = 0; i < choices.length; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(choices[i]);
            button.setCallbackData("choice" + choices[i]);

            row.add(button);

            if (row.size() == 2 || i == choices.length - 1) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        choicesKeyboardMarkup.setKeyboard(rows);
        return choicesKeyboardMarkup;
    }
}
