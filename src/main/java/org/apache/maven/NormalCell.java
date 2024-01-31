package org.apache.maven;

public class NormalCell extends Cell{
  // private int number;
  private boolean busy;

  public NormalCell(int number) {
    super(number);
    // this.number = number;
    this.busy = false;
  }

  public int action(int diceValue) {
    return super.getNumber();
  }

  public boolean isBusy() {
    return busy;
  }
  
  public int getNumber() {
    return super.getNumber();
  }
  
  public void setBusy(boolean busy) {
    this.busy = busy;
  }

}
