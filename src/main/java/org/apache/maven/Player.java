package org.apache.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
  private static final Object lock = new Object();
  private static boolean isPlayerTurn = false;
  public static void main(String[] args) {
    try {
      Socket socket = new Socket("localhost", 12345);
      System.out.println("Connected to the server.");

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      // * Create a separate instance of ServerListenerThread
      ServerListenerThread serverListenerThread = new ServerListenerThread(socket);
      serverListenerThread.start();

      // * Main thread handles user input
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
      String userInput;
      Boolean active = true;
      while (active) {
        synchronized (lock) {
          // Wait until it's the player's turn
          while (!isPlayerTurn) {
            try {
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }

        // Read user input from the console
        userInput = stdin.readLine();

        // Exit if user types "stop"
        if (userInput.equals("stop")) {
          active = false;
          break;
        }

        // Send a message to the server
        out.println(userInput);

        synchronized (lock) {
          // Set it to the server's turn and notify the server
          isPlayerTurn = false;
          lock.notify();
        }

      }

      // Close resources when done
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
