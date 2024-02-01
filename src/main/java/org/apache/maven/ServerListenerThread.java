package org.apache.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    private BufferedReader serverIn;
    private static final Object lock = new Object();
    // ! the synchronizitation is not correct
    private static boolean isPlayerTurn = true;
    private static boolean registered = false;

    public ServerListenerThread(Socket socket) throws IOException {
        this.serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String serverMessage;
            while ((serverMessage = serverIn.readLine()) != null) {
                System.out.println("from Server: " + serverMessage);
                if (!registered) {
                    System.out.println("passo");
                    synchronizeTurn();
                }
                if ("PlayerTurn".equals(serverMessage)) {
                    synchronizeTurn();
                }
                registered = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void synchronizeTurn() {
        synchronized (lock) {
            // Toggle the turn to the next player
            isPlayerTurn = true;
            lock.notify(); // Notify one waiting thread
        }
    }
}
