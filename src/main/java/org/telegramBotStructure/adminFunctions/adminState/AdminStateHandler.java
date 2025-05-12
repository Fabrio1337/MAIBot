package org.telegramBotStructure.adminFunctions.adminState;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminStateHandler implements AdminStateHandlerInterface{
    private Map<Long, AdminState> adminStates = new HashMap<>();

    //метод получения текущего состояния пользователя
    @Override
    public AdminState getAdminState(Long chatId)
    {
        return adminStates.getOrDefault(chatId, AdminState.DEFAULT);
    }

    //метод для установки текущего состояния пользователя
    @Override
    public void setAdminState(Long chatId, AdminState state)
    {
        adminStates.put(chatId, state);
    }

    //метод для сброса текущего состояния пользователя
    @Override
    public void resetAdminState(Long chatId)
    {
        adminStates.put(chatId, AdminState.DEFAULT);
    }

}

