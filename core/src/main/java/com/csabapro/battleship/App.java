package com.csabapro.battleship;

import com.csabapro.battleship.messaging.Message;

public class App {
  public static void main(String[] args) {
    var msg = Message.fromJson(
        "}");
    if (!msg.isOk)
      System.out.println(msg.unwrapErr().details());
    else
      System.out.println(msg);
  }
}
