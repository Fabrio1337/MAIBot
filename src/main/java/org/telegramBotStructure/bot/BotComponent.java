package org.telegramBotStructure.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegramBotStructure.bot.status.CheckUserOrAdmin;


@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BotComponent implements LongPollingSingleThreadUpdateConsumer {


    @Autowired
    private final CheckUserOrAdmin checkUserOrAdmin;


    @Override
    public void consume(Update update) {
        try
        {
            checkUserOrAdmin.getUpdateMessage(update);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
