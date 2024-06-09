package theController;

import Sockets.ClientSocket;
import javafx.application.Platform;
import javafx.stage.Stage;
import theModel.DataNotSendSerialized;
import theModel.DataSerialize;
import theModel.JobClasses.Enterprise;
import theModel.ParameterSerialize;
import theView.pointer.Pointer;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PointerController {
    private ClientSocket clientSocket;
    private Thread clientThread;
    private Thread threadConnection;
    private boolean connected;
    private ScheduledExecutorService scheduler;
    private CountDownLatch latch;

    private static Pointer pointer = null;
    private static Enterprise ent;
    private ArrayList<String> workhours = new ArrayList<>();
    private final DataNotSendSerialized dataNotSendSerialized;
    private final ParameterSerialize parameterSerialize;
    private String ipConnectTo = "";
    private String portConnectTo = "";

    public PointerController(Pointer p, Stage stage) throws ClassNotFoundException, IOException, InterruptedException {
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

        startUpdateEmployeeList();

        // Faire un check-in/out
        pointer.getLoginCheckInOut().getBtn2().setOnAction(e -> {
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
                    Platform.runLater(() -> Pointer.PrintAlert("Server not connected",
                            "The server is not connected. Data will be sent once the connection is restored."));
                    workhours.add(res.toString());
                    connected = false;
                }
            }
        });
    }

    public void startScheduledConnection() {
        if (scheduler.isShutdown() || scheduler.isTerminated()) {
            scheduler = Executors.newScheduledThreadPool(1);
        }

        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!connected) {
                    startConnectionThread();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
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

    public void startUpdateEmployeeList() {
        Runnable updateEmpList = () -> {
            try {
                if (ent != null) {
                    DataSerialize data = new DataSerialize();
                    data.loadData();
                    Enterprise theEnt = data.getEnterpriseClassByPort(ent.getEntPort());

                    if (theEnt != null) {
                        ArrayList<String> emp = new ArrayList<>();
                        emp.add("choose your name");
                        emp.add("reboot");
                        emp.addAll(theEnt.getAllEmployeesName());
                        // Update the ComboBox on the JavaFX Application Thread
                        Platform.runLater(() -> {
                            pointer.getEmployees().clearLCBComboBox();
                            pointer.getEmployees().setLCBComboBox(emp.toArray(new String[0]));
                        });
                    } else {
                        System.out.println("Enterprise is null");
                    }
                }
            } catch (EOFException eof) {
                System.out.println("EOFException: Possible corrupted data file. " + eof.getMessage());
            } catch (Exception e) {
                e.printStackTrace(); // Log the exception for debugging
            }
        };
        scheduler.scheduleAtFixedRate(updateEmpList, 0, 10, TimeUnit.SECONDS);
    }

    public void startConnectionThread() throws IOException, ClassNotFoundException {
        ArrayList<String> parametersConnection = parameterSerialize.loadData();

        if (parametersConnection.size() == 2) {
            ipConnectTo = parametersConnection.get(0);
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
                        sendPendingData();
                        startUpdateEmployeeList(); // Redémarrer la mise à jour de la liste des employés
                    }
                } else {
                    // Demande de renvoi des informations d'entreprise
                    clientSocket.clientSendMessage("resendEnterprise");
                    if (latch.await(2, TimeUnit.SECONDS)) {
                        ent = clientSocket.getCorrectEnterprise();
                        if (ent != null) {
                            connected = true;
                            Platform.runLater(this::reloadEmployeesCombox);
                            sendPendingData();
                            startUpdateEmployeeList(); // Redémarrer la mise à jour de la liste des employés
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted while waiting for connection.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("IOException or ClassNotFoundException during connection: " + e.getMessage());
            }
        };
        threadConnection = new Thread(connectTask);
        threadConnection.start();
    }

    private void sendPendingData() throws IOException, ClassNotFoundException {
        try {
            // Charger les données de pointage non envoyées
            ArrayList<String> notSended = dataNotSendSerialized.loadData();

            if (!notSended.isEmpty()) {
                for (String res : notSended) {
                    clientSocket.clientSendMessage(res);
                }
            }

            // Charger les heures de travail non envoyées
            if (!workhours.isEmpty()) {
                for (String res : workhours) {
                    clientSocket.clientSendMessage(res);
                }
            }

            // Vider les données de pointage non envoyées après envoi
            dataNotSendSerialized.saveData(new ArrayList<>());
            workhours.clear();
            System.out.println("Emptied the dataNotSendSerialized");
        } catch (EOFException eof) {
            System.out.println("EOFException while sending pending data: " + eof.getMessage());
        }
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
        if (ent != null) {
            DataSerialize data = new DataSerialize();
            try {
                data.loadData();
            } catch (IOException | ClassNotFoundException e) {
                // todo : ignore
            }

            Enterprise theEnt = data.getEnterpriseClassByPort(ent.getEntPort());
            if (theEnt != null) {
                ArrayList<String> empData = new ArrayList<>();
                empData.add("choose your name");
                empData.addAll(theEnt.getAllEmployeesName());
                pointer.getEmployees().clearLCBComboBox();
                pointer.getEmployees().setLCBComboBox(empData.toArray(new String[0]));
            }
        }
    }
}
