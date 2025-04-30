package org.telegramBotStructure.bot.status;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CheckUserOrAdmin {

    public void getUpdateMessage(Update update);
    public boolean isAdmin(long chatId);
}
