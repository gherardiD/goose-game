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

      // Implement client-side logic (handle user input, display game state, etc.)
      // Use in.readLine() to receive messages from the server
      // Use out.println() to send messages to the server

      // Example:
      out.println("MOVE 3");

      // Receive updates from the server
      String serverMessage;
      while ((serverMessage = in.readLine()) != null) {
        System.out.println("Server says: " + serverMessage);

        // Process the received game state update
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
