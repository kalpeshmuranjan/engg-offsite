package io.gupshup.workshop.chatgroup.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.gupshup.workshop.chatgroup.constants.Constants;
import io.gupshup.workshop.chatgroup.utils.Tools;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ErrorResponse {
    private              Object                      entity;
    private              Response.Status             status;
    private static final ParameterizedMessageFactory messageFactory = new ParameterizedMessageFactory();


    public ErrorResponse (Response.Status status, String message) {
        this.entity = getJsonObject(message);
        this.status = status;
    }


    public ErrorResponse (Response.Status status, String message, Object... params) {
        this(status, messageFactory.newMessage(message, params).getFormattedMessage());
    }


    public ErrorResponse (Response.Status status) {
        this.status = status;
    }


    public ErrorResponse addJsonElement (String key, JsonElement value) {
        if (this.entity == null) {
            this.entity = getJsonObject("");
        }
        ((JsonObject) this.entity).add(key, value);
        return this;
    }


    public ErrorResponse addKeyValuePair (String key, String value) {
        if (this.entity instanceof JsonObject) {
            ((JsonObject) this.entity).addProperty(key, value);
        }
        return this;
    }


    public ErrorResponse setEntity (Object entity) {
        this.entity = entity;
        return this;
    }


    public Response build () {
        String errorEntity = entity == null ? getJsonObject(Constants.ResponseValues.ERROR_MESSAGE).toString() : entity.toString();
        return Response.status(status).entity(errorEntity).header(Constants.HeaderPropertyKeys.CONTENT_TYPE_HEADER, MediaType.APPLICATION_JSON).build();
    }


    private Object getJsonObject (String message) {
        JsonObject jsonObject = Tools.tryParsingJsonObject(message);
        if (jsonObject == null) {
            jsonObject = new JsonObject();
            jsonObject.addProperty(Constants.JSONPropertyKeys.STATUS, Constants.ResponseValues.ERROR);
            jsonObject.addProperty(Constants.JSONPropertyKeys.MESSAGE, message);
        }
        return jsonObject;
    }
}
