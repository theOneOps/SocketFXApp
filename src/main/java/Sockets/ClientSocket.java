package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket extends Thread {
    private String ip;
    private int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientSocket(String ip, String port) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Hello, server!");
            String message;
            while ((message = in.readLine()) != null) {
                processMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Server not found or not responding from THE CLIENT: " + e.getMessage());
        } finally {
            clientClose();
        }
    }

    public void processMessage(String message) {
        // Here you can implement logic based on the message content
        if (message.equals("Hello, client"))
        System.out.println("Processing message from the server: " + message);
    }

    public void clientSendMessage(String message) {
        if (out != null) {
            System.out.println("out defines well ");
            out.println(message);
        }
    }

    public void clientClose() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Client closed");
        } catch (IOException e) {
            System.out.println("Error closing client: " + e.getMessage());
        }
    }
}
