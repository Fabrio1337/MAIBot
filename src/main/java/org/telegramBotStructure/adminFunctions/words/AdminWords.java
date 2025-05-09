package org.telegramBotStructure.adminFunctions.words;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminWords implements AdminWordsInterface {

    @Override
    public List<String> startAdminPanelWords()
    {
        return new ArrayList<>(Arrays.asList("/admin", "admin", "админ", "/админ")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public List<String> addHomeworksWords()
    {
        return new ArrayList<>(Arrays.asList("/addHomework", "addHomework",
                "добавитьДЗ", "/добавитьДЗ")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public List<String> addSubjectsWords()
    {
        return new ArrayList<>(Arrays.asList("/addSubject", "addSubject", "/добавитьПредмет", "добавитьПредмет", "добавить Предмет")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> removeSubjectsWords()
    {
        return new ArrayList<>(Arrays.asList("/removeSubject", "removeSubject", "/удалитьПредмет", "удалитьПредмет", "удалить предмет")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> addMailingWords()
    {
        return new ArrayList<>(Arrays.asList("/addMailing", "addMailing", "добавитьРассылку", "/добавитьРассылку", "добавить рассылку")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> addScheduleWords()
    {
        return new ArrayList<>(Arrays.asList("/addSchedule", "addSchedule", "добавитьРасписание", "/добавитьРасписание", "добавить расписание")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> removeScheduleWords()
    {
        return new ArrayList<>(Arrays.asList("/removeSchedule", "removeSchedule", "удалитьРасписание", "/удалитьРасписание", "удалить расписание")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> RemoveUserInGroupWords()
    {
        return new ArrayList<>(Arrays.asList("/removeUser", "removeUser", "/удалитьПользователя", "УдалитьПользователя", "удалить пользователя")).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> allWords() {
        ArrayList<String> words = new ArrayList<>();
        words.addAll(startAdminPanelWords());
        words.addAll(addHomeworksWords());
        words.addAll(removeSubjectsWords());
        words.addAll(addSubjectsWords());
        words.addAll(addMailingWords());
        words.addAll(removeScheduleWords());
        words.addAll(addScheduleWords());
        words.addAll(RemoveUserInGroupWords());
        return words;
    }
}
