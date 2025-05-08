package org.telegramBotStructure.userFunctions.words;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWords implements UserWordsInterface {
    @Override
    public List<String> userWords() {
        return new ArrayList<>(Arrays.asList("/help", "help", "команды", "/команды", "/commands", "commands")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> homeWorkWords() {
        return new ArrayList<>(Arrays.asList("/домашка", "домашка", "дз", "/дз", "домашнее задание",
                "/homework", "homework")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> scheduleWords() {
        return new ArrayList<>(Arrays.asList("/Schedule", "Schedule", "Расписание", "/Расписание")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> startWords() {
         return new ArrayList<>(Arrays.asList("/start", "старт", "start", "/старт", "начать", "/начать")) .stream()
                 .map(String::toLowerCase)
                 .collect(Collectors.toCollection(ArrayList::new));
    }
}
