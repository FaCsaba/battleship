package com.csabapro.battleship.messaging.dto;

import static com.csabapro.battleship.constants.GameConstants.MIN_SHIP_SIZE;

import com.csabapro.battleship.messaging.MessagePayload;
import com.csabapro.battleship.model.Orientation;
import com.csabapro.battleship.model.Vec2;
import com.csabapro.battleship.validation.ValidationHelper;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;

public record SetBattleshipDto(Vec2 pos, Orientation orientation, Integer size)
        implements MessagePayload {

    private static final Validator<SetBattleshipDto> validator = ValidatorBuilder.<SetBattleshipDto>of()
            .nest(SetBattleshipDto::pos, "pos", ValidationHelper.vec2Validator)
            ._object(SetBattleshipDto::orientation, "orientation", t -> t.notNull())
            ._integer(SetBattleshipDto::size, "size", t -> t.notNull().greaterThanOrEqual(MIN_SHIP_SIZE))
            .build();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Validator<MessagePayload> getValidator() {
        return (Validator) validator;
    }
}
