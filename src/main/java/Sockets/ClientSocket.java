package Sockets;

import theModel.JobClasses.Enterprise;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

/**
 * The ClientSocket class handles the client-side socket connection to a server.
 * It manages the connection, communication, and reconnection logic.
 */
public class ClientSocket implements Runnable {
    private String ip; // IP address of the server
    private int port; // Port number of the server
    private Socket clientSocket; // Client socket
    private BufferedReader in; // BufferedReader for reading messages from the server
    private PrintWriter out; // PrintWriter for sending messages to the server
    private Enterprise currentEnt = null; // Current enterprise object received from the server
    private CountDownLatch latch; // CountDownLatch to synchronize threads
    private volatile boolean runningThreadPing; // Flag to control the ping thread
    private volatile boolean serverConnected; // Flag to indicate server connection status
    private Thread pings; // Thread for sending ping messages
    private Thread readerThread; // Thread for reading server messages

    /**
     * Constructs a ClientSocket instance with specified IP, port, and latch.
     *
     * @param ip    the IP address of the server
     * @param port  the port number of the server
     * @param ilatch the CountDownLatch to synchronize threads
     */
    public ClientSocket(String ip, String port, CountDownLatch ilatch) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.latch = ilatch;
        this.serverConnected = false;
    }

    @Override
    public void run() {
        try {
            connectToServer();
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Server not found or not responding: %s\n", e.getMessage());
            latch.countDown();
        }
    }

    /**
     * Connects to the server and initializes communication streams.
     *
     * @throws IOException if an I/O error occurs during connection
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    private void connectToServer() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

        // Read the enterprise object after connection
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
    }

    /**
     * Sends periodic ping messages to the server to keep the connection alive.
     */
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
        }
        System.out.println("Ping thread stopping");
    }

    /**
     * Reads messages from the server continuously.
     */
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
        } finally {
            serverConnected = false;
            System.out.println("Server disconnected");
            clientClose();

            // Attempt to reconnect
            try {
                connectToServer();
            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Reconnection failed: %s\n", e.getMessage());
            }
        }
    }

    /**
     * Checks if the server is connected.
     *
     * @return true if the server is connected, false otherwise
     */
    public boolean isServerConnected() {
        return serverConnected;
    }

    /**
     * Stops the ping thread.
     */
    public void setRunningThreadPingToFalse() {
        this.runningThreadPing = false;
        System.out.println("Put runningThreadPing to false");
        if (pings != null) {
            pings.interrupt();
        }
    }

    /**
     * Closes the client connection and all related resources.
     */
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

    /**
     * Sends a message to the server.
     *
     * @param message the message to send
     */
    public void clientSendMessage(String message) {
        if (out != null && serverConnected) {
            out.println(message);
        } else {
            System.out.println("Cannot send message, server is disconnected");
        }
    }

    /**
     * Gets the current enterprise object.
     *
     * @return the current enterprise object
     */
    public Enterprise getCorrectEnterprise() {
        return currentEnt;
    }
}
