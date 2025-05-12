package org.telegramBotStructure.adminFunctions.adminState;

public interface AdminStateHandlerInterface {
    public AdminState getAdminState(Long chatId);
    public void setAdminState(Long chatId, AdminState state);
    public void resetAdminState(Long chatId);
}
