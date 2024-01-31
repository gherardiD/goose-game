package org.apache.maven;

// 42
public class Maze extends Cell{
  
  public Maze(int number) {
    super(number);
  }
  
  public int action(int diceValue) {
    return (super.getNumber() - diceValue);
  }
  
  public int getNumber() {
    return super.getNumber();
  }
}
