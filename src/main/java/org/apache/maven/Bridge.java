package org.apache.maven;

// 6
public class Bridge extends Cell {
  private int number;
  private int destination;

  public Bridge(int number) {
    super(number);
    this.destination = 12;
  }

  public int action(int diceValue) {
    return this.destination;
  }

  public int getNumber() {
    return number;
  }
  
}
