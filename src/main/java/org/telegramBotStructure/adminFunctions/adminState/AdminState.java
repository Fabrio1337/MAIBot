package org.telegramBotStructure.adminFunctions.adminState;

import lombok.Getter;
import lombok.Setter;

public enum AdminState {
    DEFAULT,            // Обычное состояние
    WAITING_SCHEDULE_ADD,  // Ожидание ввода для добавления расписания
    WAITING_HOMEWORK_ADD,   // Ожидание ввода для добавления ДЗ
    WAITING_INFORMATION_ADD, // Ожидание ввода для добавления информации
    WAITING_SUBJECT_ADD;// Ожидание ввода для добавления информации

    @Getter
    @Setter
    private String day;

    @Getter
    @Setter
    private String subject;

}
