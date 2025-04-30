package org.telegramBotStructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegramBotStructure.bot.BotComponent;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StartBot {
    public static void main(String args[])
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        try {
            String botToken = "12345:YOUR_TOKEN";
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, context.getBean(BotComponent.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
