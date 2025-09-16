package chat.ui;


import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class ChatClient {
    private ChatUI chatUI;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    String myIP;

    public ChatClient(String serverIP, int port) {
        chatUI = new ChatUI("LAN Messenger", this::sendMessage);
        chatUI.setStatus("Connecting...", false);
        chatUI.setSendEnabled(false);
        
        try {
            myIP = InetAddress.getLocalHost().getHostAddress();
            socket = new Socket(serverIP, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            chatUI.setStatus("Connected ✔", true);
            chatUI.setSendEnabled(true);

            new Thread(() -> {
                String msg;
                try {
                    while ((msg = input.readLine()) != null) {
                        String timestamp = "";
                        String senderIP = "";
                        String realMsg = "";
                        int timeStart = msg.indexOf('[') + 1;
                        int dash = msg.indexOf(" - ");
                        int timeEnd = msg.indexOf(']', dash);

                        if (timeStart >= 0 && dash > timeStart && timeEnd > dash) {
                            timestamp = msg.substring(timeStart, dash).trim();
                            senderIP = msg.substring(dash + 3, timeEnd).trim();
                            realMsg = msg.substring(timeEnd + 1).trim();
                        } else {
                            realMsg = msg;
                        }
                        chatUI.addMessage(realMsg, senderIP, timestamp, false, false);
                        // chatUI.addMessage(msg, peerIP, false, false); // for received messages, no ack

                    }
                } catch (IOException e) {
                    chatUI.appendMessage("System", "Connection closed.", Color.RED);
                    chatUI.setStatus("Disconnected ✘", false);
                    chatUI.setSendEnabled(false);
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server: " + e.getMessage());
            chatUI.setStatus("Disconnected ✘", false);
            chatUI.setSendEnabled(false);
        }
    }
    private String extractIP(String msg) 
    {
    // Assuming message format like: "[HH:mm:ss - 192.168.1.10] message text"
        int start = msg.indexOf(" - ");
        int end = msg.indexOf("]", start);
        if (start >= 0 && end > start) {
            return msg.substring(start + 3, end).trim();
        }
        return "Unknown";
    }

    private void sendMessage(String message) {
        if (output != null) {
            output.println(message);
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            chatUI.addMessage(message, myIP, timestamp, true, true);
            //chatUI.addMessage(message, myIP, true, true);  // for sent messages with ack

        }
    }

    public static void main(String[] args) {
        final String serverIP;
        final int port;

        if (args.length >= 2) {
            serverIP = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            serverIP = "127.0.0.1";
            port = 5000;
        }

        SwingUtilities.invokeLater(() -> new ChatClient(serverIP, port));
    }

}
