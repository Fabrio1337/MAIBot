package org.telegramBotStructure.adminFunctions.adminState;

import lombok.Getter;
import lombok.Setter;



public class AdminStateData {
    @Getter
    @Setter
    private AdminState state = AdminState.DEFAULT;

    @Getter
    @Setter
    private String day;

    @Getter
    @Setter
    private String subject;


    public AdminStateData(AdminState state) {
        this.state = state;
    }
}