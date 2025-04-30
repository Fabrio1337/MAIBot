package org.telegramBotStructure.adminFunctions.words;

import java.util.List;

public interface AdminWordsInterface {
    public List<String> startAdminPanelWords();
    public List<String> addAndRemoveHomeworksWords();
    public List<String> addAndRemoveSubjectsWords();
    public List<String> addAndRemoveMailingWords();
    public List<String> addAndRemoveScheduleWords();
    public List<String> allWords();
}
