package core;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java chat.Client <server-ip>");
            return;
        }

        String serverIP = args[0];
        int port = 5000;

        try (Socket socket = new Socket(serverIP, port)) {
            System.out.println("Connected to server.");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Thread for receiving messages
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = input.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            }).start();

            // Main thread for sending messages
            String text;
            while ((text = console.readLine()) != null) {
                output.println(text);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
