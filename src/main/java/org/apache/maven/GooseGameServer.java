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

  public void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(12345); // Use any available port
      System.out.println("Server is running and waiting for connections...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
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
}
