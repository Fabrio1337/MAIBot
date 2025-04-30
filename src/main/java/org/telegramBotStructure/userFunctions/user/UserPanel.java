package org.telegramBotStructure.userFunctions.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegramBotStructure.userFunctions.buttons.UserButtonsInterface;

@Service
public class UserPanel implements UserCommands{


    private final UserButtonsInterface userButtons;

    @Autowired
    public UserPanel(UserButtonsInterface userButtonsInterface)
    {
        this.userButtons = userButtonsInterface;
    }
}
