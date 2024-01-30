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

  // * Methods
  // Set the cells of the game
  private void createCells(){
    int [] forwardCellsNumber = new int[]{12,25,36,85,28,96,47};
    this.cells = new Cell[63];
    for(int i = 0; i < 63; i++){
      if (Arrays.asList(forwardCellsNumber).contains(i)){
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
  public void movePlayer(String playerName, int spaces) {
    if (playerPositions.containsKey(playerName)) {
      int currentPosition = playerPositions.get(playerName);
      int newPosition = currentPosition + spaces;

      // Add additional game logic/rules as needed
      // For example, check if the player landed on a special space

      playerPositions.put(playerName, newPosition);
    } else {
      System.out.println("Player not found: " + playerName);
    }
  }

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
