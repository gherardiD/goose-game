package org.apache.maven;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GooseGameServer {
  // * List of all connected clients handlers
  private static List<ClientHandler> clients = new ArrayList<>();
  // * Instance of goose game in common with all players
  private static GooseGame gooseGame = new GooseGame();

  // Start the server
  public void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(12345); // Use any available port
      Socket clientSocket;
      System.out.println("Server is running and waiting for connections...");
      
      while (true) {
        clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        // Handle client connection in a separate thread
        ClientHandler clientHandler = new ClientHandler(clientSocket, gooseGame);
        clients.add(clientHandler);
        clientHandler.sendMessage("Welcome to the Goose Game! Please enter your name:");
        new Thread(clientHandler).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Broadcast a message to all connected clients
  public static void broadcast(String message) {
    for (ClientHandler client : clients) {
      client.sendMessage(message);
    }
  }

  // send a message to a specific client
  public static void sendTo(String playerName, String message) {
    for (ClientHandler client : clients) {
      if (client.getPlayerName().equals(playerName)) {
        client.sendMessage(message);
      }
    }
  }

  // get number of connected clients
  public static int getNumberOfClients() {
    return clients.size();
  }
}
