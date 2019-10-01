package io.gupshup.workshop.chatgroup.services.impl;

import io.gupshup.workshop.chatgroup.connection.HikariConnectionPool;
import io.gupshup.workshop.chatgroup.models.Message;
import io.gupshup.workshop.chatgroup.models.User;
import io.gupshup.workshop.chatgroup.services.MessageService;

import javax.inject.Inject;
import java.sql.Connection;
import java.util.List;

public class MessageServiceImpl implements MessageService {

    @Inject
    private HikariConnectionPool connectionPool;


    @Override
    public List <Message> listMessages (String fromId) {
        return null;
    }


    @Override
    public Message sendMessage (User user, String messageContent) {
        return null;
    }
}
