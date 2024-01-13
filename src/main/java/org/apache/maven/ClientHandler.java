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

  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
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
      // Handle player actions and update game state
      String clientMessage;
      while ((clientMessage = in.readLine()) != null) {
        System.out.println("Received from client: " + clientMessage);

        // Process player action and update game state
        // For simplicity, let's assume the client sends a command like "MOVE 3" to move
        // forward 3 spaces.
        // You'll need to implement your game logic here.

        // Broadcast the updated game state to all clients
        GooseGameServer.broadcast("GameStateUpdate: " + "updatedGameState"); // Replace with your game state
      }

      // Close resources when done
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Send a message to the client
  public void sendMessage(String message) {
    out.println(message);
  }

}
