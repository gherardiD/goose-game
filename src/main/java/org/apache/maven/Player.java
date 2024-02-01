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

      // input and output streams
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
      
      String serverMessage;
      String userInput;
      Boolean registered = false;
      Boolean inGame = true;
      while (inGame) {
        
        // Wait for the server to send a message
        serverMessage = in.readLine();
        if(serverMessage == null){
          inGame = false;
          break;
        }
        System.out.println(serverMessage);
        
        if(!registered || "PlayerTurn".equals(serverMessage)){
          // Register player
          registered = true;
          
          // Read user input from the console
          userInput = stdin.readLine();
          
          // Exit if user types "stop"
          if (userInput.equals("stop")) {
            inGame = false;
            break;
          }
          
          // Send a message to the server
          out.println(userInput);
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
