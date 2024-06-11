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

/**
 * The ManageController class handles the management of enterprises, including creating,
 * configuring, and connecting to enterprises. It also manages the event handlers for
 * the application.
 * <p>
 *     The ManageController class is responsible for setting up all event handlers for the
 *     application, including the create, configure, connect, and quit buttons. It also
 *     handles the stage close event. The class also provides methods for creating a new
 *     enterprise, configuring an enterprise, connecting to an enterprise, and quitting the
 *     application.
 *     </p>
 *     <p>
 *         The class also provides methods for validating the enterprise name and port, updating
 *         the enterprise configuration, and updating the work hours that were not sent before.
 *         </p>
 *         <p>
 *             Its main attributes are :
 *             <ul>
 *                 <li>{@code manageApp} AppManagement : the AppManagement object</li>
 *                 <li>{@code dataSerialize} DataSerialize : the DataSerialize object</li>
 *                 <li>{@code serverThread} Thread : the server thread</li>
 *                 <li>{@code serversSocket} ServersSocket : the server socket</li>
 *         </p>
 *
 * @see AppManagement
 * @see DataSerialize
 * @see ServersSocket
 * @see Thread
 * @see AppWindowConnect
 * @see DataNotSendSerialized
 * @see WindowShowEnt
 * @see Pointer
 */
public class ManageController {
    private AppManagement manageApp;
    private DataSerialize dataSerialize;
    private Thread serverThread;
    private ServersSocket serversSocket;

    /**
     * Constructs a new ManageController with the given AppManagement and DataSerialize objects.
     *
     * @param Appmanagement the AppManagement object
     * @param d the DataSerialize object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public ManageController(AppManagement Appmanagement, DataSerialize d) throws IOException, ClassNotFoundException {
        this.manageApp = Appmanagement;
        this.dataSerialize = d;
        dataSerialize.loadData();

        manageApp.getAppWindowConnect().connectToEnterprise();
        reloadEnterpriseCombox();

        setupEventHandlers();
    }

    /**
     * Sets up all event handlers for the application.
     */
    private void setupEventHandlers() {
        setupCreateButtonHandler();
        setupConfigButtonHandler();
        setupConnectionButtonHandler();
        setupQuitButtonHandler();
        setupStageCloseHandler();
    }

    /**
     * Sets up the event handler for the create button.
     */
    private void setupCreateButtonHandler() {
        manageApp.getAppWindowConnect().getCreateBtn().setOnAction(e -> manageApp.getWindowCreateEnt().createEnterprise());
        setupCreateEnterpriseHandler();
    }

    /**
     * Sets up the event handler for the configuration button.
     */
    private void setupConfigButtonHandler() {
        manageApp.getAppWindowConnect().getConfigBtn().setOnAction(e -> configureEnterpriseParameters());
    }

    /**
     * Sets up the event handler for the connection button.
     */
    private void setupConnectionButtonHandler() {
        manageApp.getAppWindowConnect().getBtnConnexion().setOnAction(e -> handleEnterpriseConnection());
    }

    /**
     * Sets up the event handler for the quit button.
     */
    private void setupQuitButtonHandler() {
        manageApp.getAppWindowConnect().getQuitBtn().setOnAction(e -> handleQuit());
    }

    /**
     * Sets up the event handler for the stage close event.
     */
    private void setupStageCloseHandler() {
        manageApp.getAppWindowConnect().getStage().setOnCloseRequest(e -> {
            try {
                dataSerialize.saveData();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (serverThread != null && serverThread.isAlive() && serversSocket != null) {
                try {
                    serversSocket.shutDown();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Configures the parameters of the selected enterprise.
     */
    private void configureEnterpriseParameters() {
        String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getLCBComboBox()
                .getSelectionModel().getSelectedItem();
        if (dataSerialize.getAllEnterprises().containsKey(enterpriseValue)) {
            manageApp.getWindowConfigEnterprise().configEnterprise(dataSerialize.getEntByName(enterpriseValue));
            handleSaveConfigButton(enterpriseValue);
        }
    }

    /**
     * Handles the save configuration button event.
     *
     * @param enterpriseValue the current enterprise name
     */
    private void handleSaveConfigButton(String enterpriseValue) {
        manageApp.getWindowConfigEnterprise().getSaveConfigBtn().setOnAction(event -> {
            try {
                String newEntName = manageApp.getWindowConfigEnterprise().getNewEnterpriseName().getLTFTextFieldValue();
                if (isEnterpriseNameValid(newEntName, enterpriseValue)) {
                    updateEnterpriseConfiguration(enterpriseValue, newEntName);
                }
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Validates if the new enterprise name is valid.
     *
     * @param newEntName the new enterprise name
     * @param enterpriseValue the current enterprise name
     * @return true if the enterprise name is valid, false otherwise
     */
    private boolean isEnterpriseNameValid(String newEntName, String enterpriseValue) {
        if (newEntName.equals(enterpriseValue) || !dataSerialize.getAllEnterprises().containsKey(newEntName)) {
            return true;
        } else {
            AppWindowConnect.PrintAlert("Change of the enterprise Name", "That enterprise's name already exists in the database");
            return false;
        }
    }

    /**
     * Updates the configuration of the enterprise.
     *
     * @param enterpriseValue the current enterprise name
     * @param newEntName the new enterprise name
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    private void updateEnterpriseConfiguration(String enterpriseValue, String newEntName) throws IOException, ClassNotFoundException {
        String newEntPort = manageApp.getWindowConfigEnterprise().getNewPort().getLTFTextFieldValue();
        if (isPortValid(newEntPort)) {
            if (!enterpriseValue.equals(newEntName)) {
                dataSerialize.changeEntName(enterpriseValue, newEntName);
            }
            dataSerialize.changeEntPort(newEntName, newEntPort);
            updateWorkHoursNotSentBefore(newEntPort, dataSerialize.getEntByName(newEntName).getEntPort());
            reloadEnterpriseCombox();
            AppWindowConnect.PrintAlert("Change of configuration", "Update successfully done!");
        } else {
            AppWindowConnect.PrintAlert("Change of the enterprise Port", "Port should be only a numeric value!");
        }
    }

    /**
     * Validates if the port is valid.
     *
     * @param port the port to validate
     * @return true if the port is valid, false otherwise
     */
    private boolean isPortValid(String port) {
        return port.matches("[+-]?\\d*(\\.\\d+)?");
    }

    /**
     * Updates the work hours that were not sent before.
     *
     * @param newEntPort the new enterprise port
     * @param portValue the current port value
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    private void updateWorkHoursNotSentBefore(String newEntPort, String portValue) throws IOException, ClassNotFoundException {
        DataNotSendSerialized dataNotSerialized = new DataNotSendSerialized();
        ArrayList<String> allCheckInNotSerials = dataNotSerialized.loadData();
        for (int i = 0; i < allCheckInNotSerials.size(); i++) {
            String[] allStrs = allCheckInNotSerials.get(i).split("\\|");
            if (allStrs[0].equals(portValue)) {
                allStrs[0] = newEntPort;
            }
            allCheckInNotSerials.set(i, String.join("|", allStrs));
        }
        dataNotSerialized.saveData(allCheckInNotSerials);
    }

    /**
     * Handles the connection to the selected enterprise.
     */
    private void handleEnterpriseConnection() {
        String enterpriseValue = manageApp.getAppWindowConnect().getEnterpriseName().getLCBComboBox()
                .getSelectionModel().getSelectedItem();
        if (dataSerialize.getAllEnterprises().containsKey(enterpriseValue)) {
            serversSocket = new ServersSocket(dataSerialize, dataSerialize.getAllEnterprises().get(enterpriseValue).getEntPort());
            serverThread = new Thread(serversSocket);
            serverThread.start();
            WindowShowEnt.showEnterpriseContent(manageApp.getAppWindowConnect(), dataSerialize,
                    dataSerialize.getAllEnterprises().get(enterpriseValue), serverThread, serversSocket);
            manageApp.getAppWindowConnect().getStage().close();
            manageApp.getAppWindowConnect().setOpenViewToFalse();
            AppWindowConnect.PrintAlert(String.format("Connection to %s", enterpriseValue), "Connection succeeded");
        }
    }

    /**
     * Handles the quit button action, saving data and closing the application.
     */
    private void handleQuit() {
        try {
            dataSerialize.saveData();
            if (serverThread != null && serverThread.isAlive() && serversSocket != null) {
                serversSocket.shutDown();
            }
            manageApp.getAppWindowConnect().getStage().close();
            manageApp.getAppWindowConnect().setOpenViewToFalse();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Reloads the enterprise combo box with the updated list of enterprises.
     */
    public void reloadEnterpriseCombox() {
        ArrayList<String> allEntNames = new ArrayList<>();
        allEntNames.add("choose your enterprise");
        allEntNames.addAll(dataSerialize.getAllEnterprises().keySet());
        manageApp.getAppWindowConnect().getEnterpriseName().clearLCBComboBox();
        manageApp.getAppWindowConnect().getEnterpriseName().setLCBComboBox(allEntNames.toArray(new String[0]));
    }

    /**
     * Sets up the event handler for creating a new enterprise.
     */
    private void setupCreateEnterpriseHandler() {
        if (manageApp.getWindowCreateEnt().getAllBtns().getBtn2() != null) {
            manageApp.getWindowCreateEnt().getAllBtns().getBtn2().setOnAction(e -> createEnterprise());
        }
    }

    /**
     * Creates a new enterprise with the provided name and port.
     */
    private void createEnterprise() {
        String entName = manageApp.getWindowCreateEnt().getNewEnterpriseName().getLTFTextFieldValue();
        String entPort = manageApp.getWindowCreateEnt().getNewPort().getLTFTextFieldValue();
        if (!dataSerialize.getAllEnterprises().containsKey(entName.toLowerCase())) {
            try {
                dataSerialize.addNewEnterprise(entName, entPort);
                manageApp.getWindowCreateEnt().getNewEnterpriseName().setLTFTextFieldValue("");
                manageApp.getWindowCreateEnt().getNewPort().setLTFTextFieldValue("");
                Pointer.PrintAlert("Creation of enterprise", String.format("Enterprise '%s' succeeded", entName));
                dataSerialize.saveData();
                reloadEnterpriseCombox();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
