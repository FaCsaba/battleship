package com.csabapro.battleship.messaging;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.csabapro.battleship.messaging.dto.CreateGameDto;
import com.csabapro.battleship.messaging.dto.CreateSessionDto;
import com.csabapro.battleship.messaging.dto.JoinGameDto;
import com.csabapro.battleship.messaging.dto.SetBattleshipDto;
import com.csabapro.battleship.messaging.dto.SetTorpedoDto;
import com.csabapro.battleship.model.Orientation;
import com.csabapro.battleship.model.Result;
import com.csabapro.battleship.model.Vec2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import am.ik.yavi.message.SimpleMessageFormatter;

public record Message(MessageType type, MessagePayload payload) {

  private static Validator<Message> validator;
  private static final Gson gson = new GsonBuilder()
      .registerTypeAdapter(Message.class, MessageDeserializer.getMessageDeserializer())
      .create();

  public Message(final MessageType type, final MessagePayload payload) {
    this.type = type;
    this.payload = payload;

    validator = ValidatorBuilder.<Message>of()
        ._object(Message::type, "type", t -> t.notNull())
        .nest(Message::payload, "payload",
            Optional.ofNullable(payload).map(p -> p.getValidator())
                .orElse(ValidatorBuilder.<MessagePayload>of().build()))
        .build();
  }

  public static Message CreateSession(final String username, final boolean isBot) {
    return new Message(MessageType.CreateSession, new CreateSessionDto(username, isBot));
  }

  public static Message CreateGame(final Integer playerCount,
      final Collection<Integer> shipSizes,
      final Integer boardSize) {
    return new Message(MessageType.CreateGame, new CreateGameDto(playerCount, shipSizes, boardSize));
  }

  public static Message JoinGame(final String gameId) {
    return new Message(MessageType.JoinGame, new JoinGameDto(gameId));
  }

  public static Message SetBattleship(final Vec2 pos, final Orientation orientation,
      final Integer size) {
    return new Message(MessageType.SetBattleship, new SetBattleshipDto(pos, orientation, size));
  }

  public static Message SetTorpedo(final String boardId, final Vec2 pos) {
    return new Message(MessageType.SetTorpedo, new SetTorpedoDto(boardId, pos));
  }

  public final ConstraintViolations validate() {
    return validator.validate(this);
  }

  public static Result<Message, ConstraintViolations> fromJson(String str) {
    Message msg;
    try {
      msg = gson.fromJson(str, Message.class);
      var validationRes = msg.validate();
      if (!validationRes.isValid()) {
        return Result.Err(validationRes);
      }
    } catch (JsonSyntaxException e) {
      String[] args = { str };
      var err = ConstraintViolations.of(List.of(new ConstraintViolation("message", "message",
          e.getMessage(), args, new SimpleMessageFormatter(), Locale.getDefault())));
      return Result.Err(err);
    }

    return Result.Ok(msg);
  }

  public final String toJson() {
    return gson.toJson(this);
  }

  @Override
  public String toString() {
    return "Message [type=" + type + ", payload=" + payload + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((payload == null) ? 0 : payload.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Message other = (Message) obj;
    if (type != other.type)
      return false;
    if (payload == null) {
      if (other.payload != null)
        return false;
    } else if (!payload.equals(other.payload))
      return false;
    return true;
  }
}
