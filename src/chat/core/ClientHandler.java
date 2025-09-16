package chat.core;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private Set<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) throws IOException {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        String msg;
        try {
            while ((msg = input.readLine()) != null) {
                String clientIP = socket.getInetAddress().getHostAddress();
                String timeStampedMsg = "[" + MessageUtils.getTimestamp() + " - " + clientIP + "] " + msg;
                broadcast(timeStampedMsg);
                MessageUtils.saveMessageToLog(timeStampedMsg);
            }
        } catch (IOException e) {
            System.out.println("Connection closed: " + socket.getInetAddress());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            clientHandlers.remove(this);
        }
    }

    private void broadcast(String message) 
    {
        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                
                if (client != this) {
                    client.output.println(message);
                }
            }
        }
    }

}
