package org.telegramBotStructure.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Component
public class BotComponent implements LongPollingBot {


    private final String BOT_TOKEN = "";
    private final String BOT_NAME = "";


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
    public BotOptions getOptions() {
        return new BotOptions() {
            @Override
            public String getBaseUrl() {
                return "https://api.telegram.org/";
            }
        };
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

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
