package com.csabapro.battleship.messaging.dto;

import com.csabapro.battleship.messaging.MessagePayload;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;

public record JoinGameDto(String gameId) implements MessagePayload {
  private static final Validator<JoinGameDto> validator = ValidatorBuilder.<JoinGameDto>of()
    ._string(JoinGameDto::gameId, "gameId", t -> t.notBlank())
    .build();

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Validator<MessagePayload> getValidator() {
    return (Validator) validator;
  }
}
