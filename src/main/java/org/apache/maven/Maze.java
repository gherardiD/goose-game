package org.apache.maven;

// 42
public class Maze extends Cell{
  private int number;
  
  public Maze(int number) {
    super(number);
  }
  
  public int action(int diceValue) {
    return number - diceValue;
  }
  
  public int getNumber() {
    return number;
  }
}
