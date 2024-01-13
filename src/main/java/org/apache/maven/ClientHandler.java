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
      // Implement server-side logic here
      // Read messages from the client using in.readLine()
      // Send messages to the client using out.println()

      // Example:
      out.println("Welcome to the Goose Game!");

      // Close resources when done
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
