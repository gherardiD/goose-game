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

  public ClientHandler(Socket socket, GooseGame gooseGame) {
    this.clientSocket = socket;
    this.gooseGame = gooseGame;
    this.isRegistered = false;
    this.playerName = "";

    try {
      this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      String clientMessage = in.readLine();
      // Register player
      if (!isRegistered) {
        registerPlayer(clientMessage);
        this.playerName = clientMessage;
      }
      
      while ((clientMessage = in.readLine()) != null) {
        System.out.println("client:" + clientMessage);

        // TODO: Process player action and update game state using gooseGame instance
        processPlayerAction();
        
      }

      // Close resources when done
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Process the player action
  private void processPlayerAction() {
    int dice1 = (int) (Math.random() * 6) + 1;
    int dice2 = (int) (Math.random() * 6) + 1;
    int spaces = dice1 + dice2;
    gooseGame.movePlayer(this.playerName, spaces);
  }

  
  private void registerPlayer(String name) {
    gooseGame.addPlayer(name);
    this.isRegistered = true;
    String players = gooseGame.getPlayersName();
    GooseGameServer.broadcast("players: " + players); // Replace with your game state
  }
  
  // Send a message to the client
  public void sendMessage(String message) {
    System.out.println("sending");
    out.println(message);
  }

}
