package theView.pointer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import theModel.ParameterSerialize;
import theView.utils.AllBtns;
import theView.utils.LabeledTextFieldHBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides the UI and functionality for changing pointer configuration parameters such as IP and port.<br>
 * Its attributes are:
 * <ul>
 *     <li>{@code newPort} : LabeledTextFieldHBox- the UI component for entering a new port</li>
 *     <li>{@code newIp} : LabeledTextFieldHBox - the UI component for entering a new IP address</li>
 *     <li>{@code allBtns} : AllBtns - the UI component for action buttons (Quit, Save Configuration)</li>
 *     <li>{@code openViewConfigPointer}: Boolean - a flag to check if the configuration window is open</li>
 *     <li>{@code stage} : Stage - the stage for the configuration window</li>
 * </ul>
 *
 * @see LabeledTextFieldHBox
 * @see AllBtns
 * @see Stage
 */
public class ChangePointerConfig {

    private static LabeledTextFieldHBox newPort;   // UI component for entering a new port
    private static LabeledTextFieldHBox newIp;     // UI component for entering a new IP address
    private static AllBtns allBtns;                // UI component for action buttons (Quit, Save Configuration)
    private static Boolean openViewConfigPointer = false; // Flag to check if the configuration window is open
    private static Stage stage;                   // The stage for the configuration window

    /**
     * Gets the LabeledTextFieldHBox for the new IP address.
     *
     * @return the LabeledTextFieldHBox for the new IP address
     */
    public LabeledTextFieldHBox getNewIp() {
        return newIp;
    }

    /**
     * Initializes the configuration window components.
     */
    public void WindowConfigPointer() {
        newIp = new LabeledTextFieldHBox("IP", "");
        newIp.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Save Configuration");
        newPort = new LabeledTextFieldHBox("Port : ", "");
        newPort.setDisableToFalse();
    }

    /**
     * Opens the configuration window for changing pointer parameters.
     * Loads existing parameters if available.
     *
     * @throws IOException            if there is an issue with IO operations
     * @throws ClassNotFoundException if the class for parameter serialization is not found
     */
    public void openConfigPointer() throws IOException, ClassNotFoundException {
        if (!openViewConfigPointer) {
            // Load existing parameters
            ParameterSerialize p = new ParameterSerialize();
            ArrayList<String> params = p.loadData();

            // Set the initial values and prompts for the text fields
            newIp.setLTFTextFieldValue("");
            newIp.setPromptText("Enter IP Number");
            newPort.setLTFTextFieldValue("");
            newPort.setPromptText("Enter Port Number");

            if (!params.isEmpty()) {
                newIp.setLTFTextFieldValue(params.get(0));  // Load IP from saved parameters
                newPort.setLTFTextFieldValue(params.get(1));  // Load port from saved parameters
            }

            // Create and configure the stage
            stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Change connection parameters");

            // Set up the container for the UI elements
            VBox container = new VBox();
            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);
            container.setSpacing(10);
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(10));
            container.getChildren().addAll(newIp, newPort, spacer, allBtns);

            // Set the scene with the container
            Scene scene = new Scene(container, 400, 120);
            stage.setScene(scene);

            // Handle the window close event
            openViewConfigPointer = true;
            stage.setOnCloseRequest(e -> openViewConfigPointer = false);

            // Set actions for the buttons
            allBtns.getBtn1().setOnAction(e -> {
                stage.close();
                openViewConfigPointer = false;
            });

            // Show the stage
            stage.show();
        }
    }

    /**
     * Gets the LabeledTextFieldHBox for the new pointer IP.
     *
     * @return the LabeledTextFieldHBox for the new pointer IP
     */
    public static LabeledTextFieldHBox getNewPointerIp() {
        return newIp;
    }

    /**
     * Gets the AllBtns component containing the action buttons.
     *
     * @return the AllBtns component
     */
    public AllBtns getAllBtns() {
        return allBtns;
    }

    /**
     * Gets the LabeledTextFieldHBox for the new port.
     *
     * @return the LabeledTextFieldHBox for the new port
     */
    public LabeledTextFieldHBox getNewPort() {
        return newPort;
    }

    /**
     * Gets the status of whether the configuration view is open.
     *
     * @return true if the configuration view is open, false otherwise
     */
    public Boolean getOpenViewConfigEnt() {
        return openViewConfigPointer;
    }

    /**
     * Gets the button for quitting the configuration.
     *
     * @return the quit button
     */
    public Button getQuit() {
        return allBtns.getBtn1();
    }

    /**
     * Gets the button for saving the configuration.
     *
     * @return the save configuration button
     */
    public Button getSaveConfigBtn() {
        return allBtns.getBtn2();
    }
}
