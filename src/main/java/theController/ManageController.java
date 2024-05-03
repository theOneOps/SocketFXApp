package theController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import theModel.DataSerialize;
import Sockets.GeneralSocket;
import theView.manage.AppWindowConnect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ManageController {
    private AppWindowConnect manageApp;
    private DataSerialize dataSerialize;
    private GeneralSocket gnlSocket;

    public ManageController(AppWindowConnect appWindow, DataSerialize d, Stage stage) throws IOException,
            ClassNotFoundException, InterruptedException {
        this.manageApp = appWindow;
        this.dataSerialize = d;
        dataSerialize.loadData();

        // we load the name of enterprise in the combobox
        ArrayList<String> allEEntNames = new ArrayList<String>();
        allEEntNames.add("choose your enterprise");
        // we add all enterprises here
        allEEntNames.addAll(d.getAllEnterprises().keySet());

        // we clear the combobox
        manageApp.getEnterpriseName().clearLCBComboBox();
        //then we fill it with the new array
        manageApp.getEnterpriseName().setLCBComboBox(allEEntNames.toArray(new String[0]));


        manageApp.getBtnConnexion().setOnAction(e -> {
            //d.getAllEnterprises().containsKey(manageApp.getEnterpriseName().getSelectedValue())
            String ipValue = manageApp.getIp().getLTFTextFieldValue();
            String portValue = manageApp.getPort().getLTFTextFieldValue();
            String enterpriseValue = manageApp.getEnterpriseName().getSelectedValue();
            // we check if the ip, port given in paramters are valid
            if (!ipValue.isEmpty() &&
                    !portValue.isEmpty())
            {
                gnlSocket = new GeneralSocket(ipValue, portValue); // "192.168.0.5" // "61500"
                gnlSocket.startServer();
                try {
                    gnlSocket.startClient();
                    AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue),
                            String.format("connection at %s %s successful !", ipValue, portValue));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else
            {
                AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue),
                        "Try again with other values");
            }
        });

        manageApp.getQuitBtn().setOnAction(e -> {
            System.out.println("Quit button pressed");
            if (gnlSocket == null)
            {
                stage.close();
            }
            else {
                gnlSocket.getClientSocket().clientSendMessage("bye");
                try {
                    gnlSocket.closeClient();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Closing sockets and exiting...from the App");
                stage.close();
            }
        });
    }
}


