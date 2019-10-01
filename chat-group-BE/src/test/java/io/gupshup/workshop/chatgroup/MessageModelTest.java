package io.gupshup.workshop.chatgroup;

import io.gupshup.workshop.chatgroup.models.Message;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageModelTest {

    private static final String MESSAGE_ID = "123";
    private static final String FROM_USER  = "testUser";
    private static final String CONTENT    = "content";
    private static final long   TIMESTAMP  = 123L;


    @Test
    public void testMessageModelClass () {
        Message message = new Message();

        assertNotNull(message);

        assertNull(message.messageId());
        message.messageId(MESSAGE_ID);
        assertEquals(MESSAGE_ID, message.messageId());

        assertNull(message.content());
        message.content(CONTENT);
        assertEquals(CONTENT, message.content());


        assertNull(message.fromUser());
        message.fromUser(FROM_USER);
        assertEquals(FROM_USER, message.fromUser());

        assertEquals(0L, message.timeStamp());
        message.timeStamp(TIMESTAMP);
        assertEquals(TIMESTAMP, message.timeStamp());
    }
}
