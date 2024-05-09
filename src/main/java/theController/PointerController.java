package theController;

import ConfigCommons.ConfigPort;
import Sockets.ClientSocket;
import theModel.DataSerialize;
import theView.pointer.Pointer;

import java.io.IOException;
import java.util.ArrayList;

public class PointerController {
    private Pointer pointer;
    private DataSerialize dataSerialize;

    public PointerController(Pointer p, DataSerialize d) throws ClassNotFoundException, IOException {
        pointer = p;
        dataSerialize = d;

        dataSerialize.loadData();

        reloadEnterpriseCombox();

        pointer.getConnection().getLCBComboBox().setOnAction(e->reloadEmployeesCombox());
    }

    public void reloadEnterpriseCombox()
    {
        ArrayList<String> allEEntNames = new ArrayList<>();
        allEEntNames.add("choose your enterprise");
        // we add all enterprises here
        allEEntNames.addAll(dataSerialize.getAllEnterprises().keySet());

        // we clear the combobox
        pointer.getConnection().clearLCBComboBox();
        //then we fill it with the new array

        pointer.getConnection().setLCBComboBox(allEEntNames.toArray(new String[0]));

        reloadEmployeesCombox();

        pointer.getCheckInOut().getBtn1().setOnAction(e->{

            String entName = pointer.getConnection().getLCBComboBox().getSelectionModel().getSelectedItem();
            String empName = pointer.getEmployees().getLCBComboBox().getSelectionModel().getSelectedItem();

            if (!entName.equals("choose your enterprise") && !empName.equals("choose your name"))
            {
                String ip = pointer.getIp().getLTFTextFieldValue();
                String port = pointer.getPort().getLTFTextFieldValue();
                if (!ip.isEmpty() && !port.isEmpty())
                {
                    System.out.println("check in button clicked ! ");

                    ConfigPort.savePortConfiguration("config.properties", Integer.parseInt(port));

                    ClientSocket clientSocket = new ClientSocket(ip, port);
                    clientSocket.start();
                }
            }
        });
    }

    public void reloadEmployeesCombox()
    {
        String entName = pointer.getConnection().getLCBComboBox().getSelectionModel().getSelectedItem();

        //System.out.println("entreprise choisi " + entName);

        ArrayList<String> empData = new ArrayList<>();
        // we clear the combobox
        pointer.getEmployees().clearLCBComboBox();
        //then we fill it with the new array
        if (dataSerialize.getAllEnterprises().get(entName) != null)
        {
            empData.add("choose your name");
            empData.addAll(dataSerialize.getAllEnterprises()
                    .get(entName).getAllEmployeesName());
        }
        else
            empData.add("choose your name");

        pointer.getEmployees().setLCBComboBox(empData.toArray(new String[0]));
    }
}
