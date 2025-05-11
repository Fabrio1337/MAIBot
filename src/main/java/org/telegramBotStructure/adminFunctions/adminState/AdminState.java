package org.telegramBotStructure.adminFunctions.adminState;

public enum AdminState {
    DEFAULT,            // Обычное состояние
    WAITING_SCHEDULE_ADD,  // Ожидание ввода для добавления расписания
    WAITING_SCHEDULE_DELETE, // Ожидание ввода для удаления расписания
    WAITING_HOMEWORK_ADD,   // Ожидание ввода для добавления ДЗ
    WAITING_INFORMATION_ADD, // Ожидание ввода для добавления информации
    WAITING_SUBJECT_ADD // Ожидание ввода для добавления информации
}
