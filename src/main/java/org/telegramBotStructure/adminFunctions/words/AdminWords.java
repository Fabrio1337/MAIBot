package org.telegramBotStructure.adminFunctions.words;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AdminWords implements AdminWordsInterface {

    @Override
    public List<String> startAdminPanelWords()
    {
        return new ArrayList<>(Arrays.asList("/admin", "admin", "админ", "/админ"));
    }
    @Override
    public List<String> addAndRemoveHomeworksWords()
    {
        return new ArrayList<>(Arrays.asList("/addHomework", "addHomework",
                "/removeHomework", "removeHomework",
                "добавитьДЗ", "/добавитьДЗ",
                "удалитьДЗ", "/удалитьДЗ",
                "удалить дз", "добавить дз"));
    }
    @Override
    public List<String> addAndRemoveSubjectsWords()
    {
        return new ArrayList<>(Arrays.asList("/addSubject", "addSubject", "/добавитьПредмет", "добавитьПредмет", "добавить Предмет",
                "/removeSubject", "removeSubject", "/удалитьПредмет", "удалитьПредмет", "удалить предмет"));
    }
    @Override
    public List<String> addAndRemoveMailingWords()
    {
        return new ArrayList<>(Arrays.asList("/addMailing", "addMailing", "добавитьРассылку", "/добавитьРассылку", "добавить рассылку",
                "/removeMailing", "removeMailing", "удалитьРассылку", "/удалитьРассылку", "удалить рассылку"));
    }
    @Override
    public List<String> addAndRemoveScheduleWords()
    {
        return new ArrayList<>(Arrays.asList("/addSchedule", "addSchedule", "добавитьРасписание", "/добавитьРасписание", "добавить расписание",
                "/removeSchedule", "removeSchedule", "удалитьРасписание", "/удалитьРасписание", "удалить расписание"));
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
