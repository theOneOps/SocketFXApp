package Sockets;

import theModel.JobClasses.Enterprise;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class ClientSocket implements Runnable {
    private String ip;
    private int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Enterprise currentEnt = null;
    private CountDownLatch latch;

    public ClientSocket(String ip, String port, CountDownLatch ilatch) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.latch = ilatch;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, port);
            System.out.println("client en attente !");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

//            String message;
//            while ((message = in.readLine()) != null) {
//                processMessage(message);
//                currentEnt = (Enterprise) input.readObject();
//            }
            // Lire l'objet Enterprise dès la connexion
            currentEnt = (Enterprise) input.readObject();
            System.out.println("Received Enterprise object: " + currentEnt);

            // Signaler que l'objet a été lu
            latch.countDown();

        } catch (IOException e) {
            System.out.printf("Server not found or not responding from THE CLIENT: %s", e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void processMessage(String message) {
        // Here you can implement logic based on the message content
        if (message.equals("Hello, client")) {
            System.out.printf("Processing message from the server: %s%n",message);
        }
        else
        {
            System.out.printf("Processing other message from the server: %s%n",message);
        }
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
            System.out.printf("Error closing client: %s", e.getMessage());
        }
    }

    public Enterprise getCorrectEnterprise()
    {
        if (currentEnt != null)
            return currentEnt;

        return null;
    }
}
