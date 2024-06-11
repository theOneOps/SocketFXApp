package theController;

import Sockets.ServersSocket;
import theModel.DataNotSendSerialized;
import theModel.DataSerialize;
import theView.manage.AppManagement;
import theView.manage.AppWindowConnect;
import theView.manage.windowShowEnt.WindowShowEnt;
import theView.pointer.Pointer;
import java.io.IOException;
import java.util.ArrayList;

public class ManageController {
    private AppManagement manageApp;
    private DataSerialize dataSerialize;
    private Thread serverThread;
    private ServersSocket serversSocket;


    public ManageController(AppManagement Appmanagement, DataSerialize d) throws IOException,
            ClassNotFoundException {
        this.manageApp = Appmanagement;
        this.dataSerialize = d;
        dataSerialize.loadData();

        manageApp.getAppWindowConnect().connectToEnterprise();

        reloadEnterpriseCombox();

        manageApp.getAppWindowConnect().getCreateBtn().setOnAction(e ->
                manageApp.getWindowCreateEnt().createEnterprise());


        // button config enterpise parameters
        btnConfigParametersOfEnt();

        // button connection
        btnConnectionClicked();

        // Method to handle the closure of the windowConnect's stage
        quitConnectWindowClosedEvent();

        // Method to handle an enterprise's creation
        btnCreateEntEvent();

        manageApp.getAppWindowConnect().getStage().setOnCloseRequest(e ->{
            try {
                dataSerialize.saveData();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (serverThread != null)
            {
                if (serverThread.isAlive() && serversSocket != null) {
                    try {
                        serversSocket.shutDown();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

    }




    private void btnConfigParametersOfEnt() {
        manageApp.getAppWindowConnect().getConfigBtn().setOnAction(e -> {
            String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getLCBComboBox()
                    .getSelectionModel().getSelectedItem();
            System.out.printf("old value of the enterprise %s%n", enterpriseValue);

            if (dataSerialize.getAllEnterprises().containsKey(enterpriseValue))
            {
                    manageApp.getWindowConfigEnterprise().configEnterprise(dataSerialize.getEntByName(enterpriseValue));
                    String portValue = dataSerialize.getEntByName(enterpriseValue).getEntPort();
                    System.out.printf("old value of the enterprise's port %s%n", portValue);

                // todo :  update the port of the enterprise
                    manageApp.getWindowConfigEnterprise().getSaveConfigBtn().setOnAction(event ->{
                        try {
                            String newEntName = manageApp.getWindowConfigEnterprise().getNewEnterpriseName().getLTFTextFieldValue();
                            System.out.printf("new value of the enterprise %s%n", newEntName);

                            if (newEntName.equals(enterpriseValue) || (!dataSerialize.getAllEnterprises().containsKey(newEntName)))
                            {
                                    String newEntPort = manageApp.getWindowConfigEnterprise().getNewPort().getLTFTextFieldValue();
                                    System.out.printf("new value of the enterprise's port %s%n", newEntPort);
                                    if (newEntPort.matches("[+-]?\\d*(\\.\\d+)?"))
                                    {
                                        // change the enterprise's name if needed it !
                                        if (!enterpriseValue.equals(newEntName))
                                            dataSerialize.changeEntName(enterpriseValue,
                                                    newEntName);

                                        // change the enterprise's port's to connect with
                                        dataSerialize.changeEntPort(newEntName, newEntPort);

                                        // update of the workhours not sended before...
                                        DataNotSendSerialized dataNotSerialized = new DataNotSendSerialized();

                                        ArrayList<String> allCheckInNotSerials = dataNotSerialized.loadData();
                                        System.out.printf("the new values of the workhours : %s %n", allCheckInNotSerials);

                                        for (int i = 0; i < allCheckInNotSerials.size();i++) {
                                            String[] allStrs = allCheckInNotSerials.get(i).split("\\|");
                                            if (allStrs[0].equals(portValue))
                                                allStrs[0] = newEntPort;
                                            allCheckInNotSerials.set(i,String.join("|", allStrs));
                                        }
                                        // update workhours 'enterprise
                                        System.out.printf("the new values of the workhours : %s %n", allCheckInNotSerials);
                                        dataNotSerialized.saveData(allCheckInNotSerials);

                                        // Vérifiez que les modifications sont bien sauvegardées
                                        ArrayList<String> checkSavedData = dataNotSerialized.loadData();
                                        System.out.printf("Verified saved values of the workhours : %s %n", checkSavedData);


                                        // we reload the enterprises' values names
                                        reloadEnterpriseCombox();


                                        AppWindowConnect.PrintAlert("change of configuration ",
                                                "Update successfully done !");

                                    }
                                    else
                                        AppWindowConnect.PrintAlert("Change of the enterprise Port",
                                                "port should be only a numeric value !");
                            }
                            else
                                AppWindowConnect.PrintAlert("Change of the enterprise Name",
                                        "that enterprise's name already existed in the database");


                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
        });

    }

    public void btnConnectionClicked()
    {
        manageApp.getAppWindowConnect().getBtnConnexion().setOnAction(e -> {
            String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getLCBComboBox()
                    .getSelectionModel().getSelectedItem();

            if (dataSerialize.getAllEnterprises().containsKey(enterpriseValue))
            {

                    serversSocket = new ServersSocket(dataSerialize,
                            dataSerialize.getAllEnterprises().get(enterpriseValue).getEntPort());

                    serverThread = new Thread(serversSocket);
                    serverThread.start();

                    // print the table Vie of the enterprise
                    WindowShowEnt.showEnterpriseContent(manageApp.getAppWindowConnect(),dataSerialize,
                            dataSerialize.getAllEnterprises().get(enterpriseValue), serverThread, serversSocket);


                    manageApp.getAppWindowConnect().getStage().close();
                    manageApp.getAppWindowConnect().setOpenViewToFalse();

                    AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue),
                            "Connection succeeded");
            }
        });
    }


    public void quitConnectWindowClosedEvent()
    {
        manageApp.getAppWindowConnect().getQuitBtn().setOnAction(e -> {
            System.out.println("Quit button pressed");
            try {
                dataSerialize.saveData();
                if (serverThread != null)
                {
                    if (serverThread.isAlive() && serversSocket != null)
                        serversSocket.shutDown();
                }
                manageApp.getAppWindowConnect().getStage().close();
                manageApp.getAppWindowConnect().setOpenViewToFalse();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void reloadEnterpriseCombox()
    {
        ArrayList<String> allEEntNames = new ArrayList<>();
        allEEntNames.add("choose your enterprise");
        // we add all enterprises here
        allEEntNames.addAll(dataSerialize.getAllEnterprises().keySet());

        // we clear the combobox
        manageApp.getAppWindowConnect().getEnterpriseName().clearLCBComboBox();
        //then we fill it with the new array
        manageApp.getAppWindowConnect().getEnterpriseName().setLCBComboBox(allEEntNames.toArray(new String[0]));
    }

    public void btnCreateEntEvent() {
        if (manageApp.getWindowCreateEnt().getAllBtns().getBtn2() != null)
        {
            manageApp.getWindowCreateEnt().getAllBtns().getBtn2().setOnAction(e -> {
                String entName = manageApp.getWindowCreateEnt().getNewEnterpriseName().getLTFTextFieldValue();
                String entPort = manageApp.getWindowCreateEnt().getNewPort().getLTFTextFieldValue();


                if (!dataSerialize.getAllEnterprises().containsKey(entName.toLowerCase())) {
                        try {
                            dataSerialize.addNewEnterprise(entName, entPort);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        manageApp.getWindowCreateEnt().getNewEnterpriseName().setLTFTextFieldValue("");
                        manageApp.getWindowCreateEnt().getNewPort().setLTFTextFieldValue("");

                        System.out.println("creation of enterprise succeeded");
                        Pointer.PrintAlert( "creation of enterprise",
                                String.format("enterprise '%s' succeeded", entName));

                        try {
                            dataSerialize.saveData();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        reloadEnterpriseCombox();
                    }
            });
        }
    }
}


