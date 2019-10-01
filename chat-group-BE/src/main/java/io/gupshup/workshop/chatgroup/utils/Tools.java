package io.gupshup.workshop.chatgroup.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.annotation.Nullable;

public class Tools {

    private static final JsonParser JSON_PARSER = new JsonParser();


    @Nullable
    public static JsonObject tryParsingJsonObject (String input) {
        try {
            JsonElement parsedElement = tryParsingJsonElement(input);
            return (parsedElement != null && parsedElement.isJsonObject()) ? parsedElement.getAsJsonObject() : null;
        } catch (JsonSyntaxException ignored) {
        }
        return null;
    }


    @Nullable
    public static JsonElement tryParsingJsonElement (String input) {
        if (input == null) return null;
        try {
            return JSON_PARSER.parse(input);
        } catch (JsonSyntaxException ignored) {
        }
        return null;
    }

}
