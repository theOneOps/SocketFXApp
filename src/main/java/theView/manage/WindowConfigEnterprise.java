package theView.manage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import theModel.JobClasses.Enterprise;
import theView.utils.AllBtns;
import theView.utils.LabeledTextFieldHBox;

/**
 * <p>The WindowConfigEnterprise class provides the UI and functionality to configure an enterprise.</p>
 * <p>It includes fields for the enterprise name and port, and buttons for quitting and saving the configuration.</p>
 * <p>Its attributes are :</p>
 * <p>
 *     <ul>
 *         <li>{@code newEnterpriseName}LabeledTextFieldHBox: the input field for the new enterprise name</li>
 *         <li>{@code newPort}LabeledTextFieldHBox: the input field for the new port</li>
 *         <li>{@code allBtns}AllBtns: the buttons for quit and save configuration</li>
 *         <li>{@code openViewConfigEnt}Boolean: flag to check if the configuration window is open</li>
 *         <li>{@code stage}Stage: the stage for the configuration window</li>
 *     </ul>
 * </p>
 * @see LabeledTextFieldHBox
 * @see AllBtns
 * @see Enterprise
 * @see Stage
 */
public class WindowConfigEnterprise {

    private LabeledTextFieldHBox newEnterpriseName; // Input field for the new enterprise name
    private LabeledTextFieldHBox newPort; // Input field for the new port
    private AllBtns allBtns; // Buttons for quit and save configuration
    private Boolean openViewConfigEnt = false; // Flag to check if the configuration window is open
    private Stage stage; // The stage for the configuration window

    /**
     * Constructs a WindowConfigEnterprise instance and initializes the UI components.
     */
    public WindowConfigEnterprise() {
        newEnterpriseName = new LabeledTextFieldHBox("New Enterprise Name: ", "");
        newEnterpriseName.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Save Configuration");
        newPort = new LabeledTextFieldHBox("Port: ", "");
        newPort.setDisableToFalse();
    }

    /**
     * Opens the configuration window for the specified enterprise.
     *
     * @param oldEnt the enterprise to configure
     */
    public void configEnterprise(Enterprise oldEnt) {
        if (!openViewConfigEnt) {
            stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(String.format("Config the enterprise %s", oldEnt.getEntname()));

            VBox container = new VBox();
            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            container.setSpacing(10);
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(10));

            newEnterpriseName.setLTFTextFieldValue(oldEnt.getEntname());
            newPort.setLTFTextFieldValue(oldEnt.getEntPort());

            container.getChildren().addAll(newEnterpriseName, newPort, spacer, allBtns);

            Scene scene = new Scene(container, 400, 120);
            stage.setScene(scene);

            openViewConfigEnt = true;

            stage.setOnCloseRequest(e -> openViewConfigEnt = false);

            allBtns.getBtn1().setOnAction(e -> {
                stage.close();
                openViewConfigEnt = false;
            });

            stage.show();
        }
    }

    /**
     * Gets the input field for the new enterprise name.
     *
     * @return the input field for the new enterprise name
     */
    public LabeledTextFieldHBox getNewEnterpriseName() {
        return newEnterpriseName;
    }

    /**
     * Gets the buttons for quit and save configuration.
     *
     * @return the AllBtns instance
     */
    public AllBtns getAllBtns() {
        return allBtns;
    }

    /**
     * Gets the input field for the new port.
     *
     * @return the input field for the new port
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
        return openViewConfigEnt;
    }

    /**
     * Gets the quit button.
     *
     * @return the quit button
     */
    public Button getQuit() {
        return allBtns.getBtn1();
    }

    /**
     * Gets the save configuration button.
     *
     * @return the save configuration button
     */
    public Button getSaveConfigBtn() {
        return allBtns.getBtn2();
    }
}
