package com.csabapro.battleship.validation;

import static com.csabapro.battleship.constants.GameConstants.MAX_BOARD_SIZE;

import com.csabapro.battleship.model.Vec2;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;

public class ValidationHelper {
    public static final Validator<Vec2> vec2Validator = ValidatorBuilder.<Vec2>of()
    ._integer(Vec2::x, "x", t -> t.notNull().positiveOrZero().lessThan(MAX_BOARD_SIZE))
    ._integer(Vec2::y, "y", t -> t.notNull().positiveOrZero().lessThan(MAX_BOARD_SIZE))
    .build();
}
