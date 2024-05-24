package theController;

import Sockets.ClientSocket;
import Sockets.ServersSocket;
import javafx.stage.Stage;
import theModel.DataSerialize;
import theModel.JobClasses.Enterprise;
import theView.pointer.Pointer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class PointerController {
    private Pointer pointer;
    private ClientSocket clientSocket;
    private Thread clientThread;
    private Enterprise ent;

    public PointerController(Pointer p, DataSerialize d, Stage stage) {
        pointer = p;
        ent = null;

        pointer.getQuit().setOnAction(e->
        {
            stage.close();
            if (clientThread != null)
            {
                if (clientThread.isAlive() && clientSocket != null)
                {
                    clientSocket.clientClose();
                }
            }
        });

        pointer.getLoginCheckInOut().getBtn1().setOnAction(e->{

//            String entName = pointer.getConnection().getLCBComboBox().getSelectionModel().getSelectedItem();
//            String empName = pointer.getEmployees().getLCBComboBox().getSelectionModel().getSelectedItem();

//            if (!entName.equals("choose your enterprise") && !empName.equals("choose your name"))
//            {
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

                    System.out.println("client pas ecore termine");

                    try
                    {
                        latch.await();
                    }catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (clientSocket.getCorrectEnterprise() != null)
                    {
                        ent = clientSocket.getCorrectEnterprise();

                        if (ent == null)
                            System.out.println("ent still null");
                        else
                            reloadEmployeesCombox();
                    }

                }

            }
//            }
        });
    }

//    public void reloadEnterpriseCombox()
//    {
//        ArrayList<String> allEEntNames = new ArrayList<>();
//        allEEntNames.add("choose your enterprise");
//        // we add all enterprises here
//        allEEntNames.addAll(dataSerialize.getAllEnterprises().keySet());
//
//        // we clear the combobox
//        pointer.getConnection().clearLCBComboBox();
//        //then we fill it with the new array
//
//        pointer.getConnection().setLCBComboBox(allEEntNames.toArray(new String[0]));
//
//        reloadEmployeesCombox();
//    }

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
