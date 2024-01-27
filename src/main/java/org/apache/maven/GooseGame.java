package org.apache.maven;

import java.util.HashMap;
import java.util.Map;

public class GooseGame {
  private Map<String, Integer> playerPositions; // Map to store player positions

  public GooseGame() {
    playerPositions = new HashMap<>();
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
