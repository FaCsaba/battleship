package com.csabapro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.csabapro.battleship.messaging.Message;
import com.csabapro.battleship.model.Orientation;
import com.csabapro.battleship.model.Vec2;

public class MessageTests {

  @Test
  public void testParsingReturningTheSameMessage() {
    Collection<Message> messages = List.of(
        Message.CreateSession("CreateSessionTest", false),
        Message.CreateSession("CreateSessionTest", false),
        Message.CreateGame(2, List.of(2, 3, 3, 4, 5), 10),
        Message.CreateGame(10, List.of(2, 3, 3, 4, 5, 6, 7, 8, 9, 10), 100),
        Message.JoinGame("JoinGameTest"),
        Message.SetBattleship(new Vec2(0, 0), Orientation.Horizontal, 1));

    Collection<Message> reMessages = messages.stream().map(m -> m.toJson()).map(m -> Message.fromJson(m).unwrap())
        .collect(Collectors.toList());

    assertTrue(messages.equals(reMessages));
  }

  @Test
  public void testParsingCreateSession() {
    Map<String, Boolean> res = Map.of(
        "{}", false,
        "{\"type\":\"There's no such type\"}", false,
        "{\"payload\":{}}", false,
        "{{{asdf;asdcaec", false,
        "{\"type\":\"CreateSession\"}", false, // null payload
        "{\"type\":\"CreateSession\",\"payload\":{}}", false, // null payload
        "{\"type\":\"CreateSession\",\"payload\":{\"username\":\"username\"}}", true, // boolean default to false
        Message.CreateSession(null, false).toJson(), false,
        Message.CreateSession("Hello", false).toJson(), true);

    for (var msgAndShouldBeValid : res.entrySet()) {
      assertEquals(msgAndShouldBeValid.getKey(), msgAndShouldBeValid.getValue(),
          Message.fromJson(msgAndShouldBeValid.getKey()).isOk);
    }
  }

  @Test
  public void testParsingCreateGame() {
    Map<String, Boolean> res = new HashMap<>();

    res.putAll(Map.of(
        "{\"type\":\"CreateGame\"}", false, // null payload
        "{\"type\":\"CreateGame\",\"payload\":{}", false, // null payload
        "{\"type\":\"CreateGame\",\"payload\":{\"playerCount\":\"\"}", false // incorrect type
    ));

    // null checks
    res.putAll(Map.of(
        Message.CreateGame(0, null, 0).toJson(), false,
        Message.CreateGame(null, List.of(2, 3, 3, 4, 5), 10).toJson(), false,
        Message.CreateGame(2, null, 10).toJson(), false,
        Message.CreateGame(2, List.of(2, 3, 3, 4, 5), null).toJson(), false));

    // constrain checks
    res.putAll(Map.of(
        Message.CreateGame(1, List.of(2, 3, 3, 4, 5), 10).toJson(), false,
        Message.CreateGame(11, List.of(2, 3, 3, 4, 5), 10).toJson(), false,
        Message.CreateGame(2, List.of(0, 3, 3, 4, 5), 10).toJson(), false,
        Message.CreateGame(2, List.of(11, 3, 3, 4, 5), 10).toJson(), false,
        Message.CreateGame(1, List.of(2, 3, 3, 4, 5), 9).toJson(), false,
        Message.CreateGame(2, List.of(2, 3, 3, 4, 5), 101).toJson(), false,
        Message.CreateGame(2, List.of(2, 3), 10).toJson(), false,
        Message.CreateGame(2, List.of(2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 11), 10).toJson(), false));

    res.putAll(Map.of(
        Message.CreateGame(2, List.of(2, 3, 3, 4, 5), 10).toJson(), true,
        Message.CreateGame(10, List.of(2, 3, 3, 4, 5, 100), 100).toJson(), true));

    for (var msgAndShouldBeValid : res.entrySet()) {
      assertEquals(msgAndShouldBeValid.getKey(), msgAndShouldBeValid.getValue(),
          Message.fromJson(msgAndShouldBeValid.getKey()).isOk);
    }
  }

  @Test
  public void testParsingSetBattleship() {
    Map<String, Boolean> res = Map.of(
        "{\"type\":\"SetBattleship\"}", false, // null payload
        "{\"type\":\"SetBattleship\",\"payload\":{}", false, // null payload
        Message.SetBattleship(null, null, null).toJson(), false,
        Message.SetBattleship(null, Orientation.Horizontal, 1).toJson(), false,
        Message.SetBattleship(new Vec2(0, 0), null, 1).toJson(), false,
        Message.SetBattleship(new Vec2(0, 0), Orientation.Horizontal, null).toJson(), false,
        Message.SetBattleship(new Vec2(0, 0), Orientation.Horizontal, 1).toJson(), true);

    for (var msgAndShouldBeValid : res.entrySet()) {
      assertEquals(msgAndShouldBeValid.getKey(), msgAndShouldBeValid.getValue(),
          Message.fromJson(msgAndShouldBeValid.getKey()).isOk);
    }
  }

  @Test
  public void testParsingJoinGame() {
    Map<String, Boolean> res = Map.of(
        "{\"type\":\"JoinGame\"}", false, // null payload
        "{\"type\":\"JoinGame\",\"payload\":{}", false, // null payload
        Message.JoinGame(null).toJson(), false,
        Message.JoinGame("JoinGameTest").toJson(), true);

    for (var msgAndShouldBeValid : res.entrySet()) {
      assertEquals(msgAndShouldBeValid.getKey(), msgAndShouldBeValid.getValue(),
          Message.fromJson(msgAndShouldBeValid.getKey()).isOk);
    }
  }

  @Test
  public void testParsingSetTorpedo() {
    Map<String, Boolean> res = Map.of(
        "{\"type\":\"SetTorpedo\"}", false, // null payload
        "{\"type\":\"SetTorpedo\",\"payload\":{}", false, // null payload
        Message.SetTorpedo(null, null).toJson(), false,
        Message.SetTorpedo(null, new Vec2(0, 0)).toJson(), false,
        Message.SetTorpedo("JoinGameTest#boardId", null).toJson(), false,
        Message.SetTorpedo("JoinGameTest#boardId", new Vec2(101, 101)).toJson(), false,
        Message.SetTorpedo("JoinGameTest#boardId", new Vec2(0, 0)).toJson(), true);

    for (var msgAndShouldBeValid : res.entrySet()) {
      assertEquals(msgAndShouldBeValid.getKey(), msgAndShouldBeValid.getValue(),
          Message.fromJson(msgAndShouldBeValid.getKey()).isOk);
    }
  }
}
