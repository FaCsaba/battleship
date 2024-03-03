package com.csabapro.battleship.messaging.dto;

import com.csabapro.battleship.messaging.MessagePayload;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;

public record CreateSessionDto(String username, boolean isBot) implements MessagePayload {
  private static final Validator<CreateSessionDto> validator = ValidatorBuilder.<CreateSessionDto>of()
    ._string(CreateSessionDto::username, "username", t -> t.notBlank().greaterThan(3))
    .build();

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Validator<MessagePayload> getValidator() {
    return (Validator) validator;
  }
}
