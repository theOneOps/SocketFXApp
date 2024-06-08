package theController;

import Sockets.ClientSocket;
import javafx.application.Platform;
import javafx.stage.Stage;
import theModel.DataNotSendSerialized;
import theModel.DataSerialize;
import theModel.JobClasses.Enterprise;
import theModel.ParameterSerialize;
import theView.pointer.Pointer;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PointerController {
    private static Pointer pointer = null;
    private ClientSocket clientSocket;
    private Thread clientThread;
    private Thread threadConnection;
    private static Enterprise ent;
    private ArrayList<String> workhours = new ArrayList<>();
    private final DataNotSendSerialized dataNotSendSerialized;
    private final ParameterSerialize parameterSerialize;
    private String ipConnectTo = "";
    private String portConnectTo = "";
    private ScheduledExecutorService scheduler;
    private boolean connected;
    private CountDownLatch latch;

    public PointerController(Pointer p, DataSerialize d, Stage stage) throws ClassNotFoundException, IOException {
        pointer = p;
        ent = null;
        dataNotSendSerialized = new DataNotSendSerialized();
        parameterSerialize = new ParameterSerialize();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.connected = false;

        // Initialiser la connexion avec l'application centrale
        startScheduledConnection();

        // Fermer la fenêtre et arrêter la connexion lorsqu'on clique sur le bouton de fermeture
        pointer.getQuit().setOnAction(e -> {
            stage.close();
            stopScheduledConnection();
        });

        // Fermer la fenêtre et arrêter la connexion lorsqu'on clique sur le bouton X
        stage.setOnCloseRequest(e -> stopScheduledConnection());

        // Ouvrir la vue pour configurer les paramètres de connexion
        pointer.getLoginCheckInOut().getBtn1().setOnAction(e -> {
            try {
                pointer.getConfig().openConfigPointer();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Se connecter avec de nouveaux paramètres de configuration
        pointer.getConfig().getAllBtns().getBtn2().setOnAction(event -> {
            try {
                pointer.saveNewConfigPointer();
                restartScheduledConnection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Faire un check-in/out
        pointer.getLoginCheckInOut().getBtn2().setOnAction(e -> {
            //if ()
            if (!pointer.getEmployees().getLCBComboBox().getValue().equals("choose your name")) {
                StringBuilder res = new StringBuilder(String.format("%s", ent.getEntPort()));
                int startIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf('(');
                int endIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf(')');

                // Extraire le uuid entre parenthèses
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String result = pointer.getEmployees().getLCBComboBox().getValue().substring(startIndex + 1, endIndex);
                    res.append(String.format("|%s", result));
                }

                res.append(String.format("|%s|%s", LocalDate.now(), pointer.getDateHours().roundTime()));

                // Vérifier si le serveur est connecté
                if (clientSocket != null && clientSocket.isServerConnected()) {
                    clientSocket.clientSendMessage(res.toString());
                } else {
                    // Lorsque le serveur n'est plus connecté
                    System.out.println("server not connected anymore!");
                    workhours.add(res.toString());
                    Platform.runLater(() -> Pointer.PrintAlert("Server not connected", "The server is not connected. Data will be sent once the connection is restored."));
                    connected = false;
                }
            }
        });
    }

    public void startScheduledConnection() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!connected) {
                    startConnectionThread();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void stopScheduledConnection() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            stopConnectionThread();
        }
    }

    public void restartScheduledConnection() {
        stopScheduledConnection();
        startScheduledConnection();
    }

    public void startConnectionThread() throws IOException, ClassNotFoundException {
        ArrayList<String> parametersConnection = parameterSerialize.loadData();

        if (parametersConnection.size() == 2) {
            ipConnectTo = parametersConnection.get(0); // Use get(0) instead of getFirst
            portConnectTo = parametersConnection.get(1);
        }
        String ip = ipConnectTo;
        String port = portConnectTo;

        if (ip.isEmpty() || port.isEmpty() || !port.matches("\\d+")) {
            System.out.println("Invalid IP or Port.");
            return;
        }

        Runnable connectTask = () -> {
            try {
                latch = new CountDownLatch(1);
                clientSocket = new ClientSocket(ip, port, latch);

                clientThread = new Thread(clientSocket);
                clientThread.start();

                if (!latch.await(2, TimeUnit.SECONDS)) {
                    System.out.println("Connection timeout. Server not responding.");
                    clientSocket.clientClose();
                    clientThread.interrupt();
                    return;
                }

                if (clientSocket.getCorrectEnterprise() != null) {
                    ent = clientSocket.getCorrectEnterprise();

                    if (ent == null) {
                        System.out.println("ent still null");
                    } else {
                        connected = true;
                        Platform.runLater(this::reloadEmployeesCombox);
                        try {
                            if (workhours.isEmpty())
                                workhours = dataNotSendSerialized.loadData();
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace(); // Traiter les exceptions
                        }
                        if (!workhours.isEmpty()) {
                            for (String res : workhours)
                                clientSocket.clientSendMessage(res);

                            try {
                                dataNotSendSerialized.saveData(new ArrayList<>());
                                workhours.clear();
                                System.out.println("empty the dataNotSendSerialized");
                            } catch (IOException ex) {
                                ex.printStackTrace(); // Traiter les exceptions
                            }
                        }
                    }
                } else {
                    clientSocket.clientClose();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted while waiting for connection.");
            }
//            } finally {
//                if (clientSocket != null) {
//                    clientSocket.clientClose();
//                }
//            }
        };

        threadConnection = new Thread(connectTask);
        threadConnection.start();
    }

    public void stopConnectionThread() {
        if (threadConnection != null && threadConnection.isAlive()) {
            threadConnection.interrupt();

            if (clientThread != null && clientThread.isAlive()) {
                if (clientSocket != null) {
                    clientSocket.setRunningThreadPingToFalse();
                    clientSocket.clientClose();
                }
                clientThread.interrupt();

                if (!workhours.isEmpty()) {
                    try {
                        System.out.printf("all workhours saved : %s", workhours);
                        dataNotSendSerialized.saveData(workhours);
                        workhours.clear();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            connected = false;
            scheduler = Executors.newScheduledThreadPool(1);
            latch = new CountDownLatch(1);
        }
    }

    public void reloadEmployeesCombox() {
        ArrayList<String> empData = new ArrayList<>();
        // we clear the combobox
        pointer.getEmployees().clearLCBComboBox();
        // then we fill it with the new array
        if (ent != null) {
            empData.add("choose your name");
            empData.addAll(ent.getAllEmployeesName());
        } else {
            empData.add("choose your name");
        }

        pointer.getEmployees().setLCBComboBox(empData.toArray(new String[0]));
    }

//    public void reloadEmployeesComboxAfterConnection()
//    {
//        DataSerialize d = new DataSerialize();
//        Enterprise theEnt = d.getEntByName(ent.getEntname());
//        ArrayList<String> empData = new ArrayList<>();
//        // we clear the combobox
//        pointer.getEmployees().clearLCBComboBox();
//        // then we fill it with the new array
//        if (theEnt != null) {
//            empData.add("choose your name");
//            empData.addAll(theEnt.getAllEmployeesName());
//        } else {
//            empData.add("choose your name");
//        }
//    }
}
