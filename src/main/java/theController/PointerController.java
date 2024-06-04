package theController;

import Sockets.ClientSocket;
import javafx.stage.Stage;
import theModel.DataNotSendSerialized;
import theModel.DataSerialize;
import theModel.JobClasses.Enterprise;
import theModel.ParameterSerialize;
import theView.manage.AppWindowConnect;
import theView.pointer.Pointer;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PointerController {
    private final Pointer pointer;
    private ClientSocket clientSocket;
    private Thread clientThread;
    private Enterprise ent;
    private ArrayList<String> workhours = new ArrayList<>();
    private final DataNotSendSerialized dataNotSendSerialized;
    private final ParameterSerialize parameterSerialize;

    public PointerController(Pointer p, DataSerialize d, Stage stage) throws ClassNotFoundException, IOException {
        pointer = p;
        ent = null;
        dataNotSendSerialized = new DataNotSendSerialized();
        parameterSerialize = new ParameterSerialize();

        ArrayList<String> parametersConnection= parameterSerialize.loadData();

        if (parametersConnection.size() == 2)
        {
            pointer.getIp().setLTFTextFieldValue(parametersConnection.getFirst());
            pointer.getPort().setLTFTextFieldValue(parametersConnection.get(1));
        }


        pointer.getQuit().setOnAction(e->
        {
            stage.close();

            // save the new parameters of configuration (connection the appManager)
            try {
                parameterSerialize.saveData(parametersConnection);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (clientThread != null)
            {
                if (clientThread.isAlive())
                {
                    if (clientSocket != null)
                    {
                        clientSocket.setRunningThreadPingToFalse();
                        clientSocket.clientClose();


                    }
                    clientThread.interrupt();

                }
                if (!workhours.isEmpty())
                {
                    try {
                        dataNotSendSerialized.saveData(workhours);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        stage.setOnCloseRequest(e->{

            // save the new parameters of configuration (connection the appManager)
            try {
                parameterSerialize.saveData(parametersConnection);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (clientThread != null)
            {
                if (clientThread.isAlive())
                {
                    if (clientSocket != null)
                    {
                        clientSocket.setRunningThreadPingToFalse();
                        clientSocket.clientClose();

                    }
                    clientThread.interrupt();
                }
                if (!workhours.isEmpty())
                {
                    try {
                        dataNotSendSerialized.saveData(workhours);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        pointer.getLoginCheckInOut().getBtn1().setOnAction(e->{

            String ip = pointer.getIp().getLTFTextFieldValue();
            String port = pointer.getPort().getLTFTextFieldValue();
            if (!ip.isEmpty() && !port.isEmpty())
            {
                if (port.matches("\\d+"))
                {
                    // todo : check in function
                    System.out.println("login button clicked ! ");
                    CountDownLatch latch = new CountDownLatch(1);
                    clientSocket = new ClientSocket(ip, port, latch);

                    clientThread = new Thread(clientSocket);
                    clientThread.start();
                    try {
                        // Wait for the latch with a timeout to prevent indefinite blocking
                        if (!latch.await(2, TimeUnit.SECONDS)) {
                            System.out.println("Connection timeout. Server not responding.");
                            AppWindowConnect.PrintAlert("Connection with the server","connection failed");
                            clientSocket.clientClose();
                            clientThread.interrupt();
                            return;
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread interrupted while waiting for connection.");
                        return;
                    }

                    if (clientSocket.getCorrectEnterprise() != null)
                    {
                        ent = clientSocket.getCorrectEnterprise();

                        if (ent == null)
                            System.out.println("ent still null");
                        else
                        {
                            // update the new parameters of connection (to the appManager)
                            if (!parametersConnection.isEmpty())
                            {
                                parametersConnection.set(0, ip);
                                parametersConnection.set(1, port);
                            }
                            else
                            {
                                parametersConnection.add(ip);
                                parametersConnection.add(port);
                            }

                            reloadEmployeesCombox();
                            try {
                                if (workhours.isEmpty())
                                    workhours = dataNotSendSerialized.loadData();
                            } catch (IOException | ClassNotFoundException ex) {
                                // todo : ignore
                            }
                            if (!workhours.isEmpty())
                            {
                                for (String res : workhours)
                                    clientSocket.clientSendMessage(res);

                                try {
                                    dataNotSendSerialized.saveData(new ArrayList<>());
                                    workhours.clear();
                                    System.out.println("empty the dataNotSendSerailized");
                                } catch (IOException ex) {
                                    // todo : ignore
                                }
                            }
                        }
                    }
                    else
                    {
                        clientSocket.clientClose();
                    }
                }
            }
        });


        pointer.getLoginCheckInOut().getBtn2().setOnAction(e -> {
            if (!pointer.getEmployees().getLCBComboBox().getValue().equals("choose your name")) {
                StringBuilder res = new StringBuilder(String.format("%s", ent.getEntname()));
                int startIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf('(');
                int endIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf(')');

                // we extract the uuid from the parenthesis
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String result = pointer.getEmployees().getLCBComboBox().getValue().substring(startIndex + 1, endIndex);
                    res.append(String.format("|%s", result));
                }

                res.append(String.format("|%s|%s", LocalDate.now(), pointer.getDateHours().roundTime()));

                // Check if the server is connected
                if (clientSocket.isServerConnected()) {
                    clientSocket.clientSendMessage(res.toString());
                } else {
                    // when server is not connected anymore
                    System.out.println("server not connected anymore!");
                    workhours.add(res.toString());
                }
            }
        });


    }

    public void reloadEmployeesCombox()
    {
        ArrayList<String> empData = new ArrayList<>();
        // we clear the combobox
        pointer.getEmployees().clearLCBComboBox();
        //then we fill it with the new array
        if (ent != null)
        {
            empData.add("choose your name");
            empData.addAll(ent.getAllEmployeesName());
        }
        else
            empData.add("choose your name");

        pointer.getEmployees().setLCBComboBox(empData.toArray(new String[0]));
    }
}