package theController;


import ConfigCommons.ConfigPort;
import Sockets.ServersSocket;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
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

        // establish a connection with the clientSocket
        connectToClient();
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
                // print the table Vie of the enterprise
                WindowShowEnt.showEnterpriseContent(dataSerialize,
                        dataSerialize.getAllEnterprises().get(enterpriseValue));
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
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stage.close();
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

                //System.out.println(entName + " " + entpasswd);

                if (!dataSerialize.getAllEnterprises().containsKey(entName.toLowerCase())) {
                    if (!entpasswd.isEmpty()) {
                        try {
                            dataSerialize.addNewEnterprise(entName, entpasswd);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        manageApp.getWindowCreateEnt().getNewEnterpriseName().setLTFTextFieldValue("");
                        manageApp.getWindowCreateEnt().getNewPasswd().setLTFTextFieldValue("");

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

    public void connectToClient()
    {
        Timeline tm = new Timeline();
        tm.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> {
            int port = ConfigPort.loadPortConfiguration("config.properties");
            if (port != Integer.MAX_VALUE)
            {
                ServersSocket serversSocket = new ServersSocket(String.valueOf(port));
                serversSocket.start();
            }
            else
            {
                System.out.println("No client found yet to connect with");
            }
        }));
        tm.getKeyFrames().add(new KeyFrame(Duration.seconds(10))); // update every second
        tm.setCycleCount(Timeline.INDEFINITE); // repeat indefinitely
        tm.play();
    }
}


