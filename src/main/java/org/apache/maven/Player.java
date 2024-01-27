package org.apache.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
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
      while (true) {

        // Read user input from the console
        userInput = stdin.readLine();

        // Exit if user types "stop"
        if (userInput.equals("stop")) {
          break;
        }

        // Send a message to the server
        out.println(userInput);

        // TODO: Process the received game state update

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
