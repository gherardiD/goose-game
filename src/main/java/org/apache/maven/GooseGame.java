package org.apache.maven;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
// import java.util.stream.IntStream;

public class GooseGame {
  private Map<String, Integer> playerPositions; // Map to store player positions
  private Cell[] cells; // Array to store all cells
  
  // * Constructor
  public GooseGame() {
    playerPositions = new HashMap<>();
    createCells();
  }
  // position
  // 0 1 2 3 4 5 6 7 8 9 10 11 12 13
  // cells
  // 1 2 3 4  5 6 7 8 9 10 11 12 13 14
  
  // * Methods
  // Set the cells of the game
  private void createCells(){
    int [] forwardCellsNumber = new int[]{5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63};
    this.cells = new Cell[65];
    for(int i = 0; i < cells.length; i++){
      final int currentIndex = i; // this is needed to use i in the lambda expression
      if (Arrays.stream(forwardCellsNumber).anyMatch(x -> x == currentIndex)){
        this.cells[i] = new Forward(i);
      } else if (i == 6){
        this.cells[i] = new Bridge(i);
      } else if (i == 19){
        this.cells[i] = new TavernOfLostTime(i);
      } else if (i == 42){
        this.cells[i] = new Maze(i);
      } else if (i == 52){
        this.cells[i] = new Prison(i);
      } else if (i == 58){
        this.cells[i] = new Death(i);
      } else {
        this.cells[i] = new NormalCell(i);
      }
    }
  }
  

  // Add a player to the game
  public void addPlayer(String playerName) {
    playerPositions.put(playerName, 0); // Start player at position 0
  }

  // Move a player by a specified number of spaces
  public boolean movePlayer(String playerName, int spaces) {
    if (playerPositions.containsKey(playerName)) {
      GooseGameServer.broadcast("dice: " + spaces);
      int currentPosition = playerPositions.get(playerName);
      int nextPosition;
      int nextCell = currentPosition + spaces;
      
      nextCell = checkNextCell(nextCell);
      
      nextPosition = cells[nextCell].action(spaces);
      
      nextPosition = checkNextCell(nextPosition);
      
      if(checkWin(nextPosition)){
        GooseGameServer.broadcast("Player " + playerName + " has won the game!");
        return true;
      }
      
      if (nextPosition < 0){
        // * Player is in prison
        GooseGameServer.broadcast("Player " + playerName + " is in prison for " + Math.abs(nextPosition) + " turns");
        playerPositions.put(playerName, (nextCell));
      } else {
        // * Player is not in prison
        GooseGameServer.broadcast("Player " + playerName + " is now at position " + nextPosition);
        playerPositions.put(playerName, nextPosition);
      }
      
    } else {
      System.out.println("Player not found: " + playerName);
    }
    return false;
  }
  
  private int checkNextCell(int nextCell){;
    if (nextCell > 64){
      return (64 - (nextCell - 64));
    } else {
      return nextCell;
    }
  }
  
  private boolean checkWin(int position){
    if(position == 64){
      return true;
    }else{
      return false;}
  }


  // * getters and setters
  // Get the current positions of all players
  public Map<String, Integer> getPlayerPositions() {
    return new HashMap<>(playerPositions);
  }

  // Get the names of all players as a string
  public String getPlayersName() {
    StringBuilder sb = new StringBuilder();
    for (String playerName : playerPositions.keySet()) {
      sb.append(playerName).append(" ");
    }
    return sb.toString();
  }

  // TODO: Add additional methods as needed
}
