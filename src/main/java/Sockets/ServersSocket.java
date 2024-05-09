package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServersSocket extends Thread{
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    BufferedReader in;
    PrintWriter out;

    public ServersSocket(String port)
    {
        this.port = Integer.parseInt(port);
    }

    @Override
    public void run()
    {
        // connect to server
        try {
            serverSocket = new ServerSocket(port);
//            serverSocket.setReuseAddress(true);
            System.out.println("Serveur en attente");

            clientSocket = serverSocket.accept();
            System.out.println("Client connecté");

            in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                processMessage(message);
            }

        } catch (Exception e) {
            if (e instanceof SocketException)
                System.out.println("socket closed");
        }
    }

    public void processMessage(String message) {
        if (message.equals("Hello, server!")) {
            System.out.println("Message received from the client: " + message);
            out.println("Hello, client");
        }

        else if (message.equals("connexion"))
            System.out.println("Connexion etablie");
        else if (message.equals("bye"))
        {
            serverClose();
        }
    }

    public String serverReceiveMessage() {
        try {
            if (in != null)
                return in.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public void serverClose() {
        try {
            if (in != null) in.close();
            if (serverSocket != null) serverSocket.close();
            if (clientSocket != null)  clientSocket.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            System.out.println("Error closing server: " + e.getMessage());
        }
    }
}
