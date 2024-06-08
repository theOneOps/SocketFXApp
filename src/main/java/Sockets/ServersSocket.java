package Sockets;

import theModel.DataSerialize;
import theModel.JobClasses.WorkHourEntry;
import theView.manage.windowShowEnt.EmployeePointerView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServersSocket implements Runnable {
    private String serverPort;
    private ArrayList<ConnectionHandler> connections;
    private DataSerialize data;
    private ServerSocket server;
    private boolean isListening;
    private ExecutorService pool;

    public ServersSocket(DataSerialize d, String port)
    {
        serverPort = port;
        data = d;
        connections = new ArrayList<>();
        isListening = false;
    }

    @Override
    public void run() {
        try
        {
            server = new ServerSocket(Integer.parseInt(serverPort));
//            System.out.println("server en attente !");
            pool = Executors.newCachedThreadPool();
            while(!isListening)
            {
                Socket client = server.accept();
//                System.out.println("client connecté");
                ObjectOutputStream objOut = new ObjectOutputStream(client.getOutputStream());
                objOut.writeObject(data.getEnterpriseClassByPort(serverPort));
                objOut.flush();

                ConnectionHandler ch = new ConnectionHandler(client);
                connections.add(ch);
                pool.execute(ch);
            }

        } catch (IOException e) {
            // Ignore
        }

        finally {
            try {
                shutDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutDown() throws IOException {
        if (!server.isClosed())
        {
            server.close();
            pool.shutdown();
            isListening = true;
        }

        for (ConnectionHandler ch : connections)
        {
            ch.shutConnectionDown();
        }
    }

    class ConnectionHandler implements Runnable
    {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;


        public ConnectionHandler(Socket client)
        {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message;

                while((message = in.readLine())!= null)
                {
                    if (message.equals("ping"))
                    {
                        out.flush();
                        out.println("here");
//                        System.out.println("receive ping from clientSocket !");
                    }
                    else
                    {
                        String[]messageSplit = message.split("\\|");
                        if (messageSplit.length == 4)
                        {
                            data.addNewWorkHour(messageSplit[0], messageSplit[1],
                                    messageSplit[2], messageSplit[3]);

                            EmployeePointerView.loadWorkHour(messageSplit[1], new WorkHourEntry(messageSplit[2],messageSplit[3]));
                        }

                        System.out.printf("receive something else from client ! -> %s", message);
                    }
                }
            }
            catch(IOException e)
            {
                // todo : handle
            }
        }

        public void shutConnectionDown() throws IOException {
            if (!client.isClosed())
            {
                client.close();
                out.close();
                in.close();
            }
        }
    }
}

