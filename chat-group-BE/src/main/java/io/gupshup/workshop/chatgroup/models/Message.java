package io.gupshup.workshop.chatgroup.models;

import lombok.Data;

@Data
public class Message {
    private String messageId;
    private String fromUser;
    private long   timeStamp;
}
