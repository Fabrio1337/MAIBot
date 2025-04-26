package org.telegramBotStructure.bot.responses.user.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegramBotStructure.entity.Subject;

import java.util.List;

public interface UserButtonsInterface {
    public InlineKeyboardMarkup setWelcomeButtons();
    public InlineKeyboardMarkup setGroupsButtons(String course);
    public InlineKeyboardMarkup setSubjectButtons(List<Subject> subjects);
    public InlineKeyboardMarkup setUserChoiceButtons();


}
