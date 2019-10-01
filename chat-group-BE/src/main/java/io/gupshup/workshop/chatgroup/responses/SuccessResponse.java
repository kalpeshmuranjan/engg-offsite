package io.gupshup.workshop.chatgroup.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.gupshup.workshop.chatgroup.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class SuccessResponse {

    private static final ParameterizedMessageFactory messageFactory = new ParameterizedMessageFactory();
    private              Object                      entity;
    private              Response.Status             status;
    private              Map <String, Object>        headers        = new HashMap <>();


    public SuccessResponse (String message) {
        this.entity = getJsonObject(message);
    }


    public SuccessResponse (Object entity, Response.Status status) {
        this.entity = entity;
        this.status = status;
    }


    public SuccessResponse () {
        this.entity = getJsonObject("");
    }


    public SuccessResponse (String message, Object... params) {
        this(messageFactory.newMessage(message, params).getFormattedMessage());
    }


    private JsonObject getJsonObject (String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.JSONPropertyKeys.STATUS, Constants.ResponseValues.SUCCESS);
        if (!StringUtils.isEmpty(message)) {
            jsonObject.addProperty(Constants.JSONPropertyKeys.MESSAGE, message);
        }
        return jsonObject;
    }


    public SuccessResponse (Response.Status status) {
        this.status = status;
    }


    public SuccessResponse setEntity (Object entity) {
        this.entity = entity;
        return this;
    }


    public SuccessResponse addKeyValuePair (String key, String value) {
        if (this.entity == null) {
            this.entity = getJsonObject("");
        }
        ((JsonObject) this.entity).addProperty(key, value);
        return this;
    }


    public SuccessResponse withHeader (String key, Object value) {
        this.headers.put(key, value);
        return this;
    }


    public SuccessResponse addJsonElement (String key, JsonElement value) {
        if (this.entity == null) {
            this.entity = getJsonObject("");
        }
        ((JsonObject) this.entity).add(key, value);
        return this;
    }


    public Response build () {
        Response.ResponseBuilder responseBuilder = Response.status(status != null ? status : Response.Status.OK).entity(this.entity == null ? getJsonObject(Constants.ResponseValues.SUCCESS_MESSAGE).toString() : this.entity instanceof String ? this.entity : entity.toString());
        headers.forEach(responseBuilder::header);
        return responseBuilder.build();
    }
}