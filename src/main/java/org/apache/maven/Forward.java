package org.apache.maven;

// (5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63)
public class Forward extends Cell{
  private int number;

  public Forward(int number) {
    super(number);
  }

  public int action(int diceValue) {
    return number + diceValue;
  }

  public int getNumber() {
    return number;
  }

}
