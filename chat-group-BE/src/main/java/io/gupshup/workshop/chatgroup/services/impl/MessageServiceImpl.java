package io.gupshup.workshop.chatgroup.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.gupshup.workshop.chatgroup.connection.HikariConnectionPool;
import io.gupshup.workshop.chatgroup.models.Message;
import io.gupshup.workshop.chatgroup.models.User;
import io.gupshup.workshop.chatgroup.services.MessageService;

public class MessageServiceImpl implements MessageService {

    @Inject
    private HikariConnectionPool connectionPool;


    @Override
    public List <Message> listMessages (Long fromId) {
    	
    	List <Message> messages = new ArrayList<>();
    	try {
    		Connection con = connectionPool.getConnection();
    		PreparedStatement pstm = null;
            if(fromId != null) {
            	pstm = con.prepareStatement("select * from msgtable where id > ?");
            	pstm.setLong(1, fromId);
            	
            	ResultSet resultSet = pstm.executeQuery();
            	while(resultSet.next()) {
            		messages.add(new Message().messageId(resultSet.getLong(0)).fromUser(resultSet.getString(1))
            				.content(resultSet.getString(2)).timeStamp(resultSet.getLong(3)));
            	}
            	
            } else {
            	pstm = con.prepareStatement("select * from msgtable order by ts desc limit 20");
            	
            	ResultSet resultSet = pstm.executeQuery();
            	while(resultSet.next()) {
            		messages.add(new Message().messageId(resultSet.getLong(0)).fromUser(resultSet.getString(1))
            				.content(resultSet.getString(2)).timeStamp(resultSet.getLong(3)));
            	}
            }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        
        return messages;

    }


    @Override
    public Message sendMessage (User user, String messageContent) {
        
    	Message message = null;
    	try {
    		message = new Message().messageId(System.currentTimeMillis()).fromUser(user.userName())
    				.content(messageContent).timeStamp(System.currentTimeMillis());
    		Connection con = connectionPool.getConnection();
    		PreparedStatement pstm = con.prepareStatement("insert into msgtable values (?,?,?,?)");
    		pstm.setLong(1, message.messageId());
    		pstm.setString(2, message.fromUser());
    		pstm.setString(3, message.content());
    		pstm.setLong(4, message.timeStamp());
    		
    		int row = pstm.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return message;
    }
}
