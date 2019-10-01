package io.gupshup.workshop.chatgroup.services;

import io.gupshup.workshop.chatgroup.models.Message;
import io.gupshup.workshop.chatgroup.models.User;

import java.util.List;

public interface MessageService {
    List <Message> listMessages (String fromId);

    Message sendMessage (User user, String messageContent);
}
