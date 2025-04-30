package org.telegramBotStructure.bot;

import org.springframework.stereotype.Component;


import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Component
public class BotComponent implements LongPollingSingleThreadUpdateConsumer {





    @Override
    public void consume(Update update) {
        try
        {
            if(update.hasMessage() && update.getMessage().hasText())
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
