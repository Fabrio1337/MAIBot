package org.telegramBotStructure.userFunctions.messages.callbackAction;

import lombok.Getter;

public enum UserCallbackAction {
    BACK_TO_MENU("back_to_menu"),
    BACK_TO_SUBJECTS("back_to_subject");

    @Getter
    private final String data;

    UserCallbackAction(String data) {
        this.data = data;
    }

    public static UserCallbackAction fromCode(String code) {
        for (UserCallbackAction a : values()) {
            if (a.getData().equals(code)) return a;
        }
        return null;
    }

}
