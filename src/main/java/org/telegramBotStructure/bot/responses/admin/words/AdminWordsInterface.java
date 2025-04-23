package org.telegramBotStructure.bot.responses.admin.words;

import java.util.List;

public interface AdminWordsInterface {
    public List<String> startAdminPanelWords();
    public List<String> addAndRemoveHomeworksWords();
    public List<String> addAndRemoveSubjectsWords();
    public List<String> addAndRemoveMailingWords();
    public List<String> addAndRemoveScheduleWords();

}
