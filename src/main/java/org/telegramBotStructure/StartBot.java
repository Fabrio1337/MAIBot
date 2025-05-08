package org.telegramBotStructure;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegramBotStructure.bot.BotComponent;
import org.telegramBotStructure.bot.Client;


public class StartBot {

    public static void main(String args[])
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(context.getBean(Client.class).getBotToken(), context.getBean(BotComponent.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
