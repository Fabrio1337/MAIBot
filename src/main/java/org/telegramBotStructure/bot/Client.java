package org.telegramBotStructure.bot;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Getter
@Service
public class Client {

    private final String botToken = "";

    private final TelegramClient telegramClient;

    Client() {
        telegramClient = new OkHttpTelegramClient(botToken);
    }


}
