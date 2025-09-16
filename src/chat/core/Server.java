//package chat;

// import java.io.*;
// import java.net.*;

// public class Server {
//     public static void main(String[] args) {
//         int port = 5000; 
//         try {
//             ServerSocket serverSocket = new ServerSocket();
//             serverSocket.setReuseAddress(true); // <-- important fix
//             serverSocket.bind(new InetSocketAddress(port));

//             System.out.println("Server started. Waiting for a client...");

//             Socket socket = serverSocket.accept();
//             System.out.println("Client connected: " + socket.getInetAddress());

//             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

//             // Thread for receiving messages
//             new Thread(() -> {
//                 String msg;
//                 try {
//                     while ((msg = input.readLine()) != null) {
//                         System.out.println("Client: " + msg);
//                     }
//                 } catch (IOException e) {
//                     System.out.println("Connection closed.");
//                 }
//             }).start();

//             // Main thread for sending messages
//             String text;
//             while ((text = console.readLine()) != null) {
//                 output.println(text);
//             }

//             socket.close();
//             serverSocket.close();

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }


package chat.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static final int PORT = 5000;
    // Thread-safe set of all active client handlers
    private static final Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Server started, waiting for clients...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ClientHandler handler = new ClientHandler(clientSocket, clientHandlers);
                clientHandlers.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
