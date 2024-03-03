package com.csabapro.battleship.messaging;

import com.csabapro.battleship.messaging.dto.*;

public enum MessageType {
  CreateSession(CreateSessionDto.class),
  CreateGame(CreateGameDto.class),
  JoinGame(JoinGameDto.class),
  // SearchGame(SearchGameDto.class),
  SetBattleship(SetBattleshipDto.class),
  SetTorpedo(SetTorpedoDto.class),
  // Heartbeat(HeartbeatDto.class),
  // DispatchAck(DispatchAckDto.class),
  // DispatchSessionStart(DispatchSessionStartDto.class),
  // DispatchFoundGames(DispatchFoundGamesDto.class),
  // DispatchJoinedGame(DispatchJoinedGameDto.class),
  // DispatchGameStart(DispatchGameStartDto.class),
  // DispatchTurn(DispatchTurnDto.class),
  // DispatchTimesUp(DispatchTimesUpDto.class),
  // DispatchError(DispatchErrorDto.class),
  // DispatchTorpedo(DispatchTorpedoDto.class),
  // DispatchHit(DispatchHitDto.class)
  ;

  public final Class<? extends MessagePayload> dto;

  private MessageType(Class<? extends MessagePayload> payload) {
    this.dto = payload;
  }
}
