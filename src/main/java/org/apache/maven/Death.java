package org.apache.maven;

public class Death extends Cell{

  public Death(int number) {
    super(number);
  }

  public int action(int diceValue) {
    // return to the start
    return 0;
  }

  public int getNumber() {
    return super.getNumber();
  }
}
