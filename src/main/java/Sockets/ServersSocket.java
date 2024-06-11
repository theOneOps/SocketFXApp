package Sockets;

import theModel.DataSerialize;
import theModel.JobClasses.WorkHourEntry;
import theView.manage.windowShowEnt.EmployeePointerView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>The ServersSocket class is responsible for handling incoming client connections,
 * managing the communication between clients and the server, and processing the received messages.</p>
 * <p>It contains a nested ConnectionHandler class that handles the communication with a single client.</p>
 * <p>It implements the Runnable interface to run the server in a separate thread.</p>
 * <p>It provides methods to start and shut down the server.</p>
 * <p>It uses a ServerSocket to listen for incoming client connections and a thread pool to handle multiple clients concurrently.</p>
 * <p>It uses a list of ConnectionHandler objects to keep track of all client connections.</p>
 * <p>It uses a DataSerialize object to serialize and deserialize data.</p>
 * <p>It uses a boolean flag to indicate whether the server is listening for connections.</p>
 * <p>It uses a port number to listen on.</p>
 * <p>It uses an ExecutorService to manage the thread pool.</p>
 *
 * Its attributes are:
 * <ul>
 *     <li>{@code serverPort} String : the port to listen on</li>
 *     <li>{@code connections} List&lt;ConnectionHandler&gt; : the list of client connections</li>
 *     <li>{@code data} DataSerialize : the data serializer</li>
 *     <li>{@code server} ServerSocket : the server socket</li>
 *     <li>{@code isListening} boolean : flag indicating whether the server is listening</li>
 *     <li>{@code pool} ExecutorService : the thread pool</li>
 *     </ul>
 * @see ConnectionHandler
 * @see Runnable
 * @see DataSerialize
 * @see ServerSocket
 * @see ExecutorService
 */
public class ServersSocket implements Runnable {
    private String serverPort;
    private List<ConnectionHandler> connections;
    private DataSerialize data;
    private ServerSocket server;
    private boolean isListening;
    private ExecutorService pool;

    /**
     * Constructs a new ServersSocket with the specified data serializer and port.
     *
     * @param d the data serializer
     * @param port the port to listen on
     */
    public ServersSocket(DataSerialize d, String port) {
        serverPort = port;
        data = d;
        connections = new CopyOnWriteArrayList<>();
        isListening = false;
    }

    /**
     * Runs the server, accepting client connections and creating handlers for each connection.
     */
    @Override
    public void run() {
        try {
            server = new ServerSocket(Integer.parseInt(serverPort));
            pool = Executors.newCachedThreadPool();
            while (!isListening) {
                Socket client = server.accept();
                ObjectOutputStream objOut = new ObjectOutputStream(client.getOutputStream());
                objOut.writeObject(data.getEnterpriseClassByPort(serverPort));
                objOut.flush();

                ConnectionHandler ch = new ConnectionHandler(client, objOut);
                connections.add(ch);
                pool.execute(ch);
            }
        } catch (IOException e) {
            // Ignore
        } finally {
            try {
                shutDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Shuts down the server, closing all client connections and stopping the thread pool.
     *
     * @throws IOException if an I/O error occurs
     */
    public void shutDown() throws IOException {
        if (!server.isClosed()) {
            server.close();
            pool.shutdown();
            isListening = true;
        }

        for (ConnectionHandler ch : connections) {
            ch.shutConnectionDown();
        }
    }

    /**
     * The ConnectionHandler class handles the communication with a single client.
     */
    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private ObjectOutputStream objOut;

        /**
         * Constructs a new ConnectionHandler with the specified client socket and output stream.
         *
         * @param client the client socket
         * @param objOut the object output stream
         */
        public ConnectionHandler(Socket client, ObjectOutputStream objOut) {
            this.client = client;
            this.objOut = objOut;
        }

        /**
         * Runs the handler, reading messages from the client and processing them.
         */
        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message;

                while ((message = in.readLine()) != null) {
                    if (message.equals("ping")) {
                        out.flush();
                        out.println("here");
                    } else if (message.equals("resendEnterprise")) {
                        objOut.writeObject(data.getEnterpriseClassByPort(serverPort));
                        objOut.flush();
                    } else {
                        String[] messageSplit = message.split("\\|");
                        if (messageSplit.length == 4) {
                            data.addNewWorkHour(messageSplit[0], messageSplit[1], messageSplit[2], messageSplit[3]);
                            EmployeePointerView.loadAddWorkHour(messageSplit[1], new WorkHourEntry(messageSplit[2], messageSplit[3]));
                        }

                        System.out.printf("Received something else from client! -> %s", message);
                    }
                }
            } catch (IOException e) {
                // Handle exception
            }
        }

        /**
         * Shuts down the connection, closing all streams and the client socket.
         *
         * @throws IOException if an I/O error occurs
         */
        public void shutConnectionDown() throws IOException {
            if (!client.isClosed()) {
                client.close();
                out.close();
                in.close();
            }
        }
    }
}
