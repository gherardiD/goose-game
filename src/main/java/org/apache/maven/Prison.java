package org.apache.maven;

// 52
public class Prison extends Cell{

  public Prison(int number) {
    super(number);
  }

  public int action(int diceValue) {
    // stuck for two turns
    return -2;
  }

  public int getNumber() {
    return super.getNumber();
  }
}
