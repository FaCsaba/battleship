package com.csabapro.battleship.constants;

public class GameConstants {
  public static final int MIN_PLAYER_COUNT = 2;
  public static final int MAX_PLAYER_COUNT = 10;
  public static final int MIN_SHIP_COUNT = 5; // MAX_SHIP_COUNT is eq to boardSize
  public static final int MIN_SHIP_SIZE = 1; // MAX_SHIP_SIZE is eq to boardSize
  public static final int MIN_BOARD_SIZE = 10;
  public static final int MAX_BOARD_SIZE = 100;

  static {
    // Minimum board size must be bigger than minimum ship count because players
    // might not be able to place ships otherwise and I don't want to do a more
    // complex check logic
    assert MIN_BOARD_SIZE > MIN_SHIP_COUNT;
  }
}
