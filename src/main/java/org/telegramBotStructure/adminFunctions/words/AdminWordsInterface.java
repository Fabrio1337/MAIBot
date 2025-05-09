package org.telegramBotStructure.adminFunctions.words;

import java.util.List;

public interface AdminWordsInterface {
    public List<String> startAdminPanelWords();
    public List<String> addHomeworksWords();
    public List<String> removeSubjectsWords();
    public List<String> addSubjectsWords();
    public List<String> addMailingWords();
    public List<String> addScheduleWords();
    public List<String> removeScheduleWords();
    public List<String> allWords();
    public List<String> RemoveUserInGroupWords();
}
