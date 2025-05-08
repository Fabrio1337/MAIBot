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
        return new ArrayList<>(Arrays.asList("/admin", "admin", "админ", "/админ")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public List<String> addAndRemoveHomeworksWords()
    {
        return new ArrayList<>(Arrays.asList("/addHomework", "addHomework",
                "/removeHomework", "removeHomework",
                "добавитьДЗ", "/добавитьДЗ",
                "удалитьДЗ", "/удалитьДЗ",
                "удалить дз", "добавить дз")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public List<String> addAndRemoveSubjectsWords()
    {
        return new ArrayList<>(Arrays.asList("/addSubject", "addSubject", "/добавитьПредмет", "добавитьПредмет", "добавить Предмет",
                "/removeSubject", "removeSubject", "/удалитьПредмет", "удалитьПредмет", "удалить предмет")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public List<String> addAndRemoveMailingWords()
    {
        return new ArrayList<>(Arrays.asList("/addMailing", "addMailing", "добавитьРассылку", "/добавитьРассылку", "добавить рассылку",
                "/removeMailing", "removeMailing", "удалитьРассылку", "/удалитьРассылку", "удалить рассылку")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public List<String> addAndRemoveScheduleWords()
    {
        return new ArrayList<>(Arrays.asList("/addSchedule", "addSchedule", "добавитьРасписание", "/добавитьРасписание", "добавить расписание",
                "/removeSchedule", "removeSchedule", "удалитьРасписание", "/удалитьРасписание", "удалить расписание")) .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<String> allWords() {
        ArrayList<String> words = new ArrayList<>();
        words.addAll(startAdminPanelWords());
        words.addAll(addAndRemoveHomeworksWords());
        words.addAll(addAndRemoveSubjectsWords());
        words.addAll(addAndRemoveMailingWords());
        words.addAll(addAndRemoveScheduleWords());
        return words;
    }
}
