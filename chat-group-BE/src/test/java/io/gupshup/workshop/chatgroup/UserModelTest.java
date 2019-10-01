package io.gupshup.workshop.chatgroup;


import io.gupshup.workshop.chatgroup.models.User;
import org.junit.Assert;
import org.junit.Test;

public class UserModelTest {

    private static final String USER_NAME = "username";


    @Test
    public void createUser () {
        User user = new User();
        Assert.assertNull(user.userName());
        user.userName(USER_NAME);
        Assert.assertEquals(USER_NAME, user.userName());
    }

}
