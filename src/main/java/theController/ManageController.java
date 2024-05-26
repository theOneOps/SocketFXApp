package theController;

import Sockets.ServersSocket;
import theModel.DataSerialize;
import theView.manage.AppManagement;
import theView.manage.AppWindowConnect;
import theView.manage.WindowShowEnt;
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

        manageApp.getAppWindowConnect().getCreateBtn().setOnAction(e->
                manageApp.getWindowCreateEnt().createEnterprise());

        // button connection
        btnConnectionClicked();

        // Method to handle the closure of the windowConnect's stage
        quitConnectWindowClosedEvent();

        // Method to handle an enterprise's creation
        btnCreateEntEvent();

        manageApp.getAppWindowConnect().getStage().setOnCloseRequest(e->{
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

    public void btnConnectionClicked()
    {
        manageApp.getAppWindowConnect().getBtnConnexion().setOnAction(e -> {
            String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getLCBComboBox()
                    .getSelectionModel().getSelectedItem();
            String PasswordValue = manageApp.getAppWindowConnect().getPassword().getLTFTextFieldValue();

            if (dataSerialize.getAllEnterprises().containsKey(enterpriseValue))
            {
                if (!PasswordValue.isEmpty() && PasswordValue.equals(dataSerialize.getAllEnterprises().get(enterpriseValue)
                        .getEntpasswd())) {


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

                } else {
                    AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue),
                            "Try again with other password");
                }
            }
            else
                AppWindowConnect.PrintAlert(String.format("Connection to an enterprise '%s'", enterpriseValue),
                        "that enterprise doesn't exist");
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
                String entpasswd = manageApp.getWindowCreateEnt().getNewPasswd().getLTFTextFieldValue();
                String entPort = manageApp.getWindowCreateEnt().getNewPort().getLTFTextFieldValue();


                if (!dataSerialize.getAllEnterprises().containsKey(entName.toLowerCase())) {
                    if (!entpasswd.isEmpty()) {
                        try {
                            dataSerialize.addNewEnterprise(entName, entpasswd, entPort);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        manageApp.getWindowCreateEnt().getNewEnterpriseName().setLTFTextFieldValue("");
                        manageApp.getWindowCreateEnt().getNewPasswd().setLTFTextFieldValue("");
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
                } else {
                    AppWindowConnect.PrintAlert(String.format("Creation of the enterprise %s", entName),
                            "Creation failed because an enterprise with this name already exists");
                }
            });
        }

    }
}


