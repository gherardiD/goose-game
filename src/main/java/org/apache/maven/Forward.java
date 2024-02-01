package org.apache.maven;

// (5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63)
public class Forward extends Cell{

  public Forward(int number) {
    super(number);
  }

  public int action(int diceValue) {
    GooseGameServer.broadcast("You landed on the Forward! Go forward " + diceValue + " cells");
    return (super.getNumber() + diceValue);
  }

  public int getNumber() {
    return super.getNumber();
  }

}
