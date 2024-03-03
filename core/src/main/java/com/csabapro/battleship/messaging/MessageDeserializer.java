package com.csabapro.battleship.messaging;

import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class MessageDeserializer implements JsonDeserializer<Message> {
  private static final JsonDeserializer<Message> messageDeserializer = new MessageDeserializer();

  private MessageDeserializer() {
  }

  public static JsonDeserializer<Message> getMessageDeserializer() {
    return messageDeserializer;
  }

  @Override
  public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    final JsonObject root = json.getAsJsonObject();
    final var msgTypeJson = root.get("type");
    if (msgTypeJson == null)
      return new Message(null, null);
    final var msgType = getMessageType(msgTypeJson.getAsString());
    if (msgType == null)
        throw new JsonSyntaxException("Message Type is incorrect");
    final var payload = new GsonBuilder().create().fromJson(root.get("payload"), msgType.dto);
    final var msg = new Message(msgType, payload);
    return msg;
  }

  private MessageType getMessageType(String typeAsString) {
    try {
      return MessageType.valueOf(typeAsString);
    } catch (Exception e) {
      return null;
    }
  }

}
