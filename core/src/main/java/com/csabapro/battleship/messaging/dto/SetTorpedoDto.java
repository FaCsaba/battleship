package com.csabapro.battleship.messaging.dto;

import com.csabapro.battleship.messaging.MessagePayload;
import com.csabapro.battleship.model.Vec2;
import com.csabapro.battleship.validation.ValidationHelper;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;

public record SetTorpedoDto(String boardId, Vec2 pos) implements MessagePayload {
  private static final Validator<SetTorpedoDto> validator = ValidatorBuilder.<SetTorpedoDto>of()
    ._string(SetTorpedoDto::boardId, "boardId", t -> t.notBlank())
    .nest(SetTorpedoDto::pos, "pos", ValidationHelper.vec2Validator)
    .build();

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Validator<MessagePayload> getValidator() {
    return (Validator) validator;
  }
}
