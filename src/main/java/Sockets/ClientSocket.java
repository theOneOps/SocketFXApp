package Sockets;

import theModel.JobClasses.Enterprise;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

public class ClientSocket implements Runnable {
    private String ip;
    private int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Enterprise currentEnt = null;
    private CountDownLatch latch;
    private volatile boolean runningThreadPing;
    private volatile boolean serverConnected;
    private Thread pings;
    private Thread readerThread;

    public ClientSocket(String ip, String port, CountDownLatch ilatch) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.latch = ilatch;
        this.serverConnected = false; // Initial state is disconnected
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            // We read the enterprise object just after the connection
            currentEnt = (Enterprise) input.readObject();
            latch.countDown();

            this.serverConnected = true;
            this.runningThreadPing = true;

            // Start the ping thread
            pings = new Thread(this::pingServer);
            pings.start();

            // Start the reader thread
            readerThread = new Thread(this::readServerMessages);
            readerThread.start();

        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Server not found or not responding: %s\n", e.getMessage());
            latch.countDown();
        }
    }

    private void pingServer() {
        while (runningThreadPing) {
            try {
                if (out != null) {
                    out.println("ping");
                    System.out.println("ping still running");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                runningThreadPing = false;
            }
        }
        System.out.println("Ping thread stopping");
    }

    private void readServerMessages() {
        try {
            String serverMessage;
            while (serverConnected && (serverMessage = in.readLine()) != null) {
                System.out.printf("Server message: %s\n", serverMessage);
                // Additional message handling can be added here
            }
        } catch (SocketException e) {
            System.out.println("Connection reset, server might be down");
        } catch (IOException e) {
            System.out.println("IOException while reading from server: " + e.getMessage());
        }
        finally {
            serverConnected = false;
            System.out.println("Server disconnected");
            clientClose();
        }
    }

    public boolean isServerConnected() {
        return serverConnected;
    }

    public void setRunningThreadPingToFalse() {
        this.runningThreadPing = false;
        System.out.println("Put runningThreadPing to false");
        if (pings != null) {
            pings.interrupt(); // Interrupts the thread to exit sleep immediately
        }
    }

    public void clientClose() {
        try {
            setRunningThreadPingToFalse();
            serverConnected = false;
            if (readerThread != null) {
                readerThread.interrupt();
            }
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Client closed");
        } catch (IOException e) {
            System.out.printf("Error closing client: %s\n", e.getMessage());
        }
    }

    public void clientSendMessage(String message) {
        if (out != null && serverConnected) {
            out.println(message);
        } else {
            System.out.println("Cannot send message, server is disconnected");
        }
    }

    public Enterprise getCorrectEnterprise() {
        return currentEnt;
    }
}
