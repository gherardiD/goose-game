package org.apache.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;
  private GooseGame gooseGame;
  private Boolean isRegistered;
  private String playerName;
  private int inPrison;

  // * CONSTRUCTOR

  public ClientHandler(Socket socket, GooseGame gooseGame) {
    this.clientSocket = socket;
    this.gooseGame = gooseGame;
    this.isRegistered = false;
    this.playerName = "";
    this.inPrison = 0;

    try {
      this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // * MAIN METHOD
  @Override
  public void run() {
    try {
      String clientMessage = in.readLine();
      // Register player
      if (!isRegistered) {
        this.playerName = clientMessage;
        registerPlayer(clientMessage);
      }

      boolean win = false;
      while ((clientMessage = in.readLine()) != null) {
        System.out.println("client:" + clientMessage);

        win = processPlayerAction();

        if (win) {
          GooseGameServer.setGameFinished();
          break;
        }

      }

      // Close resources when done
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // * GAME METHODS

  private void registerPlayer(String name) {
    gooseGame.addPlayer(name);
    this.isRegistered = true;
    // String players = gooseGame.getPlayersName();
    // GooseGame.broadcast("players: " + players);
  }

  // Process the player action
  private boolean processPlayerAction() {
    int dice1 = (int) (Math.random() * 6) + 1;
    int dice2 = (int) (Math.random() * 6) + 1;
    int spaces = dice1 + dice2;
    return gooseGame.movePlayer(this.playerName, spaces);
  }

  // Send a message to the client
  public void sendMessage(String message) {
    out.println(message);
  }

  public void decreasePrisonTurns() {
    this.inPrison--;
  }

  // * GETTERS AND SETTERS

  public void setPrisonTurns(int turns) {
    this.inPrison = turns;
  }

  public int getPrisonTurns() {
    return this.inPrison;
  }

  public String getPlayerName() {
    return this.playerName;
  }

}
