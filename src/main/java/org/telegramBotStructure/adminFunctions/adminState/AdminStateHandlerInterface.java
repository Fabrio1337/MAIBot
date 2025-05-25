package org.telegramBotStructure.adminFunctions.adminState;

public interface AdminStateHandlerInterface {
    AdminStateData getAdminState(Long chatId);
    void setAdminState(Long chatId, AdminState state);
    void setAdminStateData(Long chatId, AdminStateData data);
    void resetAdminState(Long chatId);
}