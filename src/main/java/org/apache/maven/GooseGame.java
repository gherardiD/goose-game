package org.apache.maven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GooseGame {

  private static List<ClientHandler> clientHandlers; // List to store all connected clientHandlers
  private Map<String, Integer> playerPositions; // Map to store player positions
  private Cell[] cells; // Array to store all cells
  private int playerTurn = 0;

  // * CONSTRUCTOR
  public GooseGame() {
    clientHandlers = new ArrayList<>();
    playerPositions = new HashMap<>();
    createCells();
  }

  // * STATIC METHODS

  // Broadcast a message to all connected clientHandlers
  public static void broadcast(String message) {
    for (ClientHandler client : clientHandlers) {
      client.sendMessage(message);
    }
  }

  // send a message to a specific client
  public static void sendTo(String playerName, String message) {
    String clientName;
    for (ClientHandler client : clientHandlers) {
      clientName = client.getPlayerName();
      if (clientName == playerName) {
        client.sendMessage(message);
      }
    }
  }

  // * START METHODS

  // Create the cells of the game
  private void createCells() {
    int[] forwardCellsNumber = new int[] { 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63 };
    this.cells = new Cell[65];
    for (int i = 0; i < cells.length; i++) {
      final int currentIndex = i; // this is needed to use i in the lambda expression
      if (Arrays.stream(forwardCellsNumber).anyMatch(x -> x == currentIndex)) {
        this.cells[i] = new Forward(i);
      } else if (i == 6) {
        this.cells[i] = new Bridge(i);
      } else if (i == 19) {
        this.cells[i] = new TavernOfLostTime(i);
      } else if (i == 42) {
        this.cells[i] = new Maze(i);
      } else if (i == 52) {
        this.cells[i] = new Prison(i);
      } else if (i == 58) {
        this.cells[i] = new Death(i);
      } else {
        this.cells[i] = new NormalCell(i);
      }
    }
  }

  // Add a client handler to the game
  public void addClientHandler(ClientHandler clientHandler) {
    clientHandlers.add(clientHandler);
  }

  // * GAME METHODS

  // Add a player to the game
  public void addPlayer(String playerName) {
    playerPositions.put(playerName, 0); // Start player at position 0
    int playerRegisteredNumber = playerPositions.size();
    int numberOfPlayers = getNumberOfClients();
    System.out.println("player registered " + playerRegisteredNumber);
    System.out.println("total players " + numberOfPlayers);
    if (playerRegisteredNumber == numberOfPlayers) {
      // All players are connected
      setPlayersTurn();
    }
  }

  private void setPlayersTurn() {
    if (playerTurn == playerPositions.size()) {
      playerTurn = 0;
    }
    String playerName = (String) playerPositions.keySet().toArray()[playerTurn];
    if (getPrisonTurns(playerName) > 0) {
      decreasePrisonTurns(playerName);
      playerTurn++;
      setPlayersTurn();
      return;
    }
    System.out.println("turn is " + playerTurn);
    sendTo(playerName, "PlayerTurn");
    playerTurn++;
  }

  // Move a player by a specified number of spaces
  public boolean movePlayer(String playerName, int spaces) {
    if (playerPositions.containsKey(playerName)) {
      broadcast("dice: " + spaces);
      int currentPosition = playerPositions.get(playerName);
      int nextPosition;
      int nextCell = currentPosition + spaces;

      nextCell = checkNextCell(nextCell);

      nextPosition = cells[nextCell].action(spaces);

      nextPosition = checkNextCell(nextPosition);

      if (checkWin(nextPosition)) {
        broadcast("Player " + playerName + " has won the game!");
        return true;
      }

      if (nextPosition < 0) {
        int prisonTurns = Math.abs(nextPosition);
        broadcast("Player " + playerName + " is in prison for " + prisonTurns + " turns");
        setPrisonTurns(playerName, prisonTurns);
        playerPositions.put(playerName, (nextCell));
      } else {
        // * Player is not in prison
        broadcast("Player " + playerName + " is now at position " + nextPosition);
        playerPositions.put(playerName, nextPosition);
      }

      // * Start next turn
      setPlayersTurn();

    } else {
      System.out.println("Player not found: " + playerName);
    }
    return false;
  }

  private int checkNextCell(int nextCell) {
    if (nextCell > 64) {
      return (64 - (nextCell - 64));
    } else {
      return nextCell;
    }
  }

  private boolean checkWin(int position) {
    if (position == 64) {
      return true;
    } else {
      return false;
    }
  }

  public void decreasePrisonTurns(String playerName) {
    for (ClientHandler client : clientHandlers) {
      if (client.getPlayerName() == playerName) {
        client.decreasePrisonTurns();
      }
    }
  }

  // * GETTERS AND SETTERS

  public void setPrisonTurns(String playerName, int turns) {
    for (ClientHandler client : clientHandlers) {
      if (client.getPlayerName() == playerName) {
        client.setPrisonTurns(turns);
      }
    }
  }

  public int getPrisonTurns(String playerName) {
    for (ClientHandler client : clientHandlers) {
      if (client.getPlayerName() == playerName) {
        return client.getPrisonTurns();
      }
    }
    return -1;
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

  // get number of connected clientHandlers
  public static int getNumberOfClients() {
    return clientHandlers.size();
  }

}
