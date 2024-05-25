package theController;

import Sockets.ClientSocket;
import javafx.stage.Stage;
import theModel.DataSerialize;
import theModel.JobClasses.Enterprise;
import theView.pointer.Pointer;

import java.time.LocalDate;
import java.time.LocalTime;
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
                    System.out.println("okkk");
                    clientSocket = new ClientSocket(ip, port, latch);
                    System.out.println("okkk");


                    clientThread = new Thread(clientSocket);
                    clientThread.start();
                    System.out.println("okkk");
                    try
                    {
                        latch.await();
                        System.out.println("okkk");
                    }catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("okkk");

                    if (clientSocket.getCorrectEnterprise() != null)
                    {
                        ent = clientSocket.getCorrectEnterprise();

                        if (ent == null)
                            System.out.println("ent still null");
                        else
                            reloadEmployeesCombox();
                    }
                    else
                    {
                        clientSocket.clientClose();
                    }

                }

            }
//            }
        });

        pointer.getLoginCheckInOut().getBtn2().setOnAction(e->{
            StringBuilder res = new StringBuilder(String.format("%s", ent.getEntname()));
            int startIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf('(');
            int endIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf(')');

            // Extraire le texte entre les parenthèses
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                String result = pointer.getEmployees().getLCBComboBox().getValue().substring(startIndex + 1, endIndex);
                res.append(String.format("|%s", result));
            }

            res.append(String.format("|%s|%s", LocalDate.now(), pointer.getDateHours().roundTime()));
            //System.out.println(res);
            clientSocket.clientSendMessage(res.toString());
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
