package org.telegramBotStructure.adminFunctions.messages.callbackAction;

import lombok.Getter;
public enum AdminCallbackAction {
    BACK_TO_MENU("back_to_menu"),
    BACK_TO_SUBJECTS("back_to_subjects"),
    BACK_TO_SCHEDULE("back_to_schedules"),
    BACK_TO_COURSES("back_to_course");

    @Getter
    private final String data;

    AdminCallbackAction(String data) {
        this.data = data;
    }

    public static AdminCallbackAction fromCode(String code) {
        for (AdminCallbackAction a : values()) {
            if (a.getData().equals(code)) return a;
        }
        return null;
    }
}
