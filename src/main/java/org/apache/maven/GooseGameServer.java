package org.apache.maven;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GooseGameServer {
  private static List<ClientHandler> clients = new ArrayList<>();

  public void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(12345); // Use any available port
      System.out.println("Server is running and waiting for connections...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        // Handle client connection in a separate thread
        ClientHandler clientHandler = new ClientHandler(clientSocket);
        new Thread(clientHandler).start();

        // Implement game logic using GooseGame class
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
