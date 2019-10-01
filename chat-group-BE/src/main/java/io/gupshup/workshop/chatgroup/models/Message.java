package io.gupshup.workshop.chatgroup.models;

import lombok.Data;

@Data
public class Message {
    private long   messageId;
    private String fromUser;
    private String content;
    private long   timeStamp;
}
