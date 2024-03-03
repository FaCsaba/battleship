package com.csabapro.battleship.messaging.dto;

import static com.csabapro.battleship.constants.GameConstants.MAX_BOARD_SIZE;
import static com.csabapro.battleship.constants.GameConstants.MAX_PLAYER_COUNT;
import static com.csabapro.battleship.constants.GameConstants.MIN_BOARD_SIZE;
import static com.csabapro.battleship.constants.GameConstants.MIN_PLAYER_COUNT;
import static com.csabapro.battleship.constants.GameConstants.MIN_SHIP_COUNT;
import static com.csabapro.battleship.constants.GameConstants.MIN_SHIP_SIZE;

import java.util.Collection;

import com.csabapro.battleship.messaging.MessagePayload;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;

public record CreateGameDto(
        Integer playerCount,
        Collection<Integer> shipSizes,
        Integer boardSize) implements MessagePayload {

    private static Validator<CreateGameDto> validator = ValidatorBuilder.<CreateGameDto>of()
            .constraint(CreateGameDto::playerCount, "playerCount",
                    t -> t.notNull().greaterThanOrEqual(MIN_PLAYER_COUNT).lessThanOrEqual(MAX_PLAYER_COUNT))
            .constraint(CreateGameDto::boardSize, "boardSize",
                    t -> t.notNull().greaterThanOrEqual(MIN_BOARD_SIZE).lessThanOrEqual(MAX_BOARD_SIZE))
            .constraint(CreateGameDto::shipSizes, "shipSizes", t -> t.notNull())
            .constraintOnTarget(t -> {
                if (t.shipSizes == null || t.boardSize == null)
                    return false;
                var count = t.shipSizes.size();
                return MIN_SHIP_COUNT <= count && count <= t.boardSize;
            }, "shipSizes",
                    "shipSizes.count",
                    "There must be at least " + MIN_SHIP_COUNT + " \"shipSizes\" and at max \"boardSize\".")
            .constraintOnTarget(
                    t -> t.shipSizes == null || t.boardSize == null
                            ? false
                            : t.shipSizes.stream().allMatch(x -> MIN_SHIP_SIZE <= x && x <= t.boardSize),
                    "shipSizes",
                    "shipSizes", "Incorrect ship size.")
            .build();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Validator<MessagePayload> getValidator() {
        return ((Validator) validator);
    }
}
