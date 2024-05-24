package theController;


import Sockets.ServersSocket;
import javafx.stage.Stage;
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
    Thread serverThread;
    private ServersSocket serversSocket;


    public ManageController(AppManagement Appmanagement, DataSerialize d, Stage stage) throws IOException,
            ClassNotFoundException {
        this.manageApp = Appmanagement;
        this.dataSerialize = d;
        dataSerialize.loadData();

        reloadEnterpriseCombox();

        manageApp.getAppWindowConnect().getCreateBtn().setOnAction(e->
                manageApp.getWindowCreateEnt().createEnterprise());

        // button connection
        btnConnectionClicked(stage);

        // Method to handle the closure of the windowConnect's stage
        quitConnectWindowClosedEvent(stage);

        // Method to handle an enterprise's creation
        btnCreateEntEvent();
    }

    public void btnConnectionClicked(Stage stage)
    {
        manageApp.getAppWindowConnect().getBtnConnexion().setOnAction(e -> {
            //d.getAllEnterprises().containsKey(manageApp.getEnterpriseName().getSelectedValue())
            String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getLCBComboBox()
                    .getSelectionModel().getSelectedItem();
            String PasswordValue = manageApp.getAppWindowConnect().getPassword().getLTFTextFieldValue();
            // we check if the ip, port given in parameters are valid
            if (!PasswordValue.isEmpty() && PasswordValue.equals(dataSerialize.getAllEnterprises().get(enterpriseValue)
                    .getEntpasswd()) &&
                    !enterpriseValue.equals("choose your  enterprise")) {


                serversSocket = new ServersSocket(dataSerialize,
                        dataSerialize.getAllEnterprises().get(enterpriseValue).getEntPort());

                serverThread = new Thread(serversSocket);
                serverThread.start();

                // print the table Vie of the enterprise
                WindowShowEnt.showEnterpriseContent(dataSerialize,
                        dataSerialize.getAllEnterprises().get(enterpriseValue), serverThread, serversSocket);
                stage.close();
                AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue),
                        "Connection succeeded");

            } else {
                AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue),
                        "Try again with other values");
            }
        });
    }


    public void quitConnectWindowClosedEvent(Stage stage)
    {
        manageApp.getAppWindowConnect().getQuitBtn().setOnAction(e -> {
            System.out.println("Quit button pressed");
            try {
                dataSerialize.saveData();
                if (serverThread.isAlive() && serversSocket != null)
                    serversSocket.shutDown();
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


