package org.apache.maven;

// 19
public class TavernOfLostTime extends Cell{
  private int number;

  public TavernOfLostTime(int number) {
    super(number);
  }

  public int action(int diceValue) {
    // stuck for a turn
    return -1;
  }

  public int getNumber() {
    return number;
  }
}
