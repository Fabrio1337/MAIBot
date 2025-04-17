package org.telegramBotStructure;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegramBotStructure.bot.BotComponent;

public class StartBot {
    public static void main(String args[])
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(context.getBean(BotComponent.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
