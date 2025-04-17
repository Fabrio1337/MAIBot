package org.telegramBotStructure.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotComponent extends TelegramLongPollingBot {

    private String BOT_TOKEN = "";
    private String BOT_NAME = "";
    @Override
    public void onUpdateReceived(Update update) {
        try
        {
            if(update.hasMessage() && update.getMessage().hasText())
            {

            }
        }
        catch (TelegramApiException e)
        {

        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}
