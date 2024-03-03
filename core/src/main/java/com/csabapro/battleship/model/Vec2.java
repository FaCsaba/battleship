package com.csabapro.battleship.model;

public record Vec2(int x, int y) {
  public boolean isInRectangle(Vec2 rectCorner1, Vec2 rectCorner2) {
    int xLowerBound = rectCorner1.x < rectCorner2.x ? rectCorner1.x : rectCorner2.x;
    int xUpperBound = rectCorner1.x > rectCorner2.x ? rectCorner1.x : rectCorner2.x;
    int yLowerBound = rectCorner1.y < rectCorner2.y ? rectCorner1.y : rectCorner2.y;
    int yUpperBound = rectCorner1.y > rectCorner2.y ? rectCorner1.y : rectCorner2.y;

    return this.x >= xLowerBound && this.x <= xUpperBound && this.y >= yLowerBound && this.y <= yUpperBound;
  }
}
