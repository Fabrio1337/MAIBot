package org.telegramBotStructure.userFunctions.words;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserWords implements UserWordsInterface {
    @Override
    public List<String> userWords() {
        return new ArrayList<>(Arrays.asList("/help", "help", "команды", "/команды", "/commands", "commands"));
    }

    @Override
    public List<String> homeWorkWords() {
        return new ArrayList<>(Arrays.asList("/домашка", "домашка", "дз", "/дз", "домашнее задание",
                "/homework", "homework"));
    }

    @Override
    public List<String> scheduleWords() {
        return new ArrayList<>(Arrays.asList("/Schedule", "Schedule", "Расписание", "/Расписание"));
    }

    @Override
    public List<String> startWords() {
         return new ArrayList<>(Arrays.asList("/start", "старт", "start", "/старт", "начать", "/начать"));
    }
}
