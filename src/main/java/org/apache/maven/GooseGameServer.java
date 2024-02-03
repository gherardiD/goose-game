package org.apache.maven;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GooseGameServer {
  // * List of all connected clients handlers
  // private static List<ClientHandler> clientHandlers = new ArrayList<>();
  // * Instance of goose game in common with all players
  private static GooseGame gooseGame = new GooseGame();

  private static boolean gameFinished = false;

  // Start the server
  public void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(12345); // Use any available port
      Socket clientSocket;
      System.out.println("Server is running and waiting for connections...");

      while (!gameFinished) {
        clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        // Handle client connection in a separate thread
        ClientHandler clientHandler = new ClientHandler(clientSocket, gooseGame);
        gooseGame.addClientHandler(clientHandler);
        clientHandler.sendMessage("Welcome to the Goose Game! Please enter your name:");
        new Thread(clientHandler).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void setGameFinished() {
    gameFinished = true;
  }
}
