package org.apache.maven;

// 52
public class Prison extends Cell{
  private int number;

  public Prison(int number) {
    super(number);
  }

  public int action(int diceValue) {
    // stuck for two turns
    return -2;
  }

  public int getNumber() {
    return number;
  }
}
