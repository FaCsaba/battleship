package com.csabapro.battleship.model;

public class Result<Ok, Err> {
  private final Ok ok;
  private final Err err;
  public final boolean isOk;

  private Result(Ok ok, Err err, boolean isOk) {
    this.ok = ok;
    this.err = err;
    this.isOk = isOk;
  }

  public static final <Ok, Err> Result<Ok, Err> Ok(final Ok ok) {
    Result<Ok, Err> res = new Result<>(ok, null, true);
    return res;
  }

  public static final <Ok, Err> Result<Ok, Err> Err(final Err err) {
    Result<Ok, Err> res = new Result<>(null, err, false);
    return res;
  }

  public final Ok unwrap() {
    assert this.isOk : "Tried to unwrap a non Ok value";
    return this.ok;
  }

  public final Err unwrapErr() {
    assert !this.isOk : "Tried to unwrapError an Ok value";
    return this.err;
  }

  @Override
  public String toString() {
    var sb = new StringBuilder("Result.");
    if (isOk) {
      sb.append("Ok(")
        .append(this.ok.toString())
        .append(")");
    } else {
      sb.append("Err(")
        .append(this.err.toString())
        .append(")");
    }
    return sb.toString();
  }
}
