package com.csabapro.battleship.messaging;

import am.ik.yavi.core.Validator;

public interface MessagePayload {
  Validator<MessagePayload> getValidator();
}
