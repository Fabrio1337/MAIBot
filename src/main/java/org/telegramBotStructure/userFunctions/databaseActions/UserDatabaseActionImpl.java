package org.telegramBotStructure.userFunctions.databaseActions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.telegramBotStructure.DatabaseDAO.DatabaseMethods;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserDatabaseActionImpl implements UserDatabaseAction {

    @Getter
    protected final DatabaseMethods databaseMethods;


}
