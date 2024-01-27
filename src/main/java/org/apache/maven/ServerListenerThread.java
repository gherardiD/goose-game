package org.apache.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListenerThread extends Thread {
  private BufferedReader serverIn;

  public ServerListenerThread(Socket socket) throws IOException {
    this.serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  @Override
  public void run() {
    try {
      String serverMessage;
      while ((serverMessage = serverIn.readLine()) != null) {
        System.out.println("from Server: " + serverMessage);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
