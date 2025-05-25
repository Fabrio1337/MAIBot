package org.telegramBotStructure.adminFunctions.adminState;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminStateHandler implements AdminStateHandlerInterface {
    private Map<Long, AdminStateData> adminStates = new HashMap<>();

    // Метод получения текущего состояния пользователя
    @Override
    public AdminStateData getAdminState(Long chatId) {
        return adminStates.getOrDefault(chatId, new AdminStateData(AdminState.DEFAULT));
    }

    // Метод для установки текущего состояния пользователя
    @Override
    public void setAdminState(Long chatId, AdminState state) {
        AdminStateData data = adminStates.getOrDefault(chatId, new AdminStateData(AdminState.DEFAULT));
        data.setState(state);
        adminStates.put(chatId, data);
    }

    // Метод для установки данных состояния
    @Override
    public void setAdminStateData(Long chatId, AdminStateData stateData) {
        adminStates.put(chatId, stateData);
    }

    // Метод для сброса текущего состояния пользователя
    @Override
    public void resetAdminState(Long chatId) {
        adminStates.put(chatId, new AdminStateData(AdminState.DEFAULT));
    }

    // Удобные методы для работы с subject
    public void setSubject(Long chatId, String subject) {
        AdminStateData data = getAdminState(chatId);
        data.setSubject(subject);
        adminStates.put(chatId, data);
    }

    public String getSubject(Long chatId) {
        return getAdminState(chatId).getSubject();
    }
}
