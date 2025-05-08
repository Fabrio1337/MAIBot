package org.telegramBotStructure.bot;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class Client {

    @Getter
    private final String botToken = "7569873625:AAGLkUSgXGXFmivRURAhjWsoxz3-SqaLrF0";

    @Getter
    private final TelegramClient telegramClient;

    Client() {
        telegramClient = new OkHttpTelegramClient(botToken);
    }


}
