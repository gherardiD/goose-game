package org.apache.maven;

public abstract class Cell {
  private int number;

  public Cell(int number) {
    this.number = number;
  }
  
  public abstract int action(int diceValue);

  public int getNumber() {
    return number;
  }
  
}
