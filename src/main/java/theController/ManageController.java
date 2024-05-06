package theController;


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

    public ManageController(AppManagement Appmanagement, DataSerialize d, Stage stage) throws IOException,
            ClassNotFoundException {
        this.manageApp = Appmanagement;
        this.dataSerialize = d;
        dataSerialize.loadData();

        reloadEnterpriseCombox();

        manageApp.getAppWindowConnect().getCreateBtn().setOnAction(e->{
            manageApp.getWindowCreateEnt().createEnterprise();
        });

        // button connection
        btnConnectionClicked(stage);

        // Connect Window btn set Action
        quitConnectWindowClosedEvent(stage);

        btnCreateEntEvent();
    }

    public void btnConnectionClicked(Stage stage)
    {
        manageApp.getAppWindowConnect().getBtnConnexion().setOnAction(e -> {
            //d.getAllEnterprises().containsKey(manageApp.getEnterpriseName().getSelectedValue())
            String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getSelectedValue();
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
        ArrayList<String> allEEntNames = new ArrayList<String>();
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

                System.out.println(entName + " " + entpasswd);

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
}


