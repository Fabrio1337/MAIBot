package org.telegramBotStructure.adminFunctions.buttons;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegramBotStructure.entity.Subject;

import java.util.Set;

public interface AdminButtonInterface {
    public InlineKeyboardMarkup adminChoice(String text);
    public EditMessageReplyMarkup setAdminChoiceMessage(long chatId, int messageId,String text);
    public InlineKeyboardMarkup scheduleDays(String text);
    public EditMessageReplyMarkup setScheduleDays(long chatId, int messageId, String text);
    public EditMessageReplyMarkup setSubjectButtons(long chatId, int messageId, Set<Subject> subjects, String data);
}
