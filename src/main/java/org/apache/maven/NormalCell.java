package org.apache.maven;

public class NormalCell extends Cell{
  private int number;
  private boolean busy;

  public NormalCell(int number) {
    super(number);
    this.busy = false;
  }

  public int action(int diceValue) {
    return number;
  }

  public boolean isBusy() {
    return busy;
  }
  
  public int getNumber() {
    return number;
  }
  
  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  public void setNumber(int number) {
    this.number = number;
  }

}
