package theView.manage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import theView.utils.AllBtns;
import theView.utils.LabeledTextFieldHBox;

/**
 * <p>The WindowCreateEnt class provides the UI and functionality to create a new enterprise.
 * <p>It includes fields for the enterprise name and port, and buttons for quitting and creating the enterprise.</p>
 * Its main attributes are :<br>
 * <ul>
 *     <li>{@code newEnterpriseName} LabeledTextFieldHBox : the input field for the new enterprise name</li>
 *     <li>{@code allBtns} AllBtns : the buttons for quit and create enterprise</li>
 *     <li>{@code newPort} LabeledTextFieldHBox : the input field for the new port</li>
 *     <li>{@code openViewCreateEnt} Boolean : flag to check if the creation window is open</li>
 *     </ul>
 *
 * @see LabeledTextFieldHBox
 * @see AllBtns
 * @see WindowCreateEnt
 */
public class WindowCreateEnt {

    private LabeledTextFieldHBox newEnterpriseName; // Input field for the new enterprise name
    private AllBtns allBtns; // Buttons for quit and create enterprise
    private LabeledTextFieldHBox newPort; // Input field for the new port
    private Boolean openViewCreateEnt = false; // Flag to check if the creation window is open

    /**
     * Constructs a WindowCreateEnt instance and initializes the UI components.
     */
    public WindowCreateEnt() {
        newEnterpriseName = new LabeledTextFieldHBox("Enterprise Name: ", "");
        newEnterpriseName.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Create");
        newPort = new LabeledTextFieldHBox("Port: ", "");
        newPort.setDisableToFalse();
    }

    /**
     * Opens the creation window for a new enterprise.
     */
    public void createEnterprise() {
        if (!openViewCreateEnt) {
            Stage stage = new Stage();
            stage.setTitle("Create Enterprise");
            stage.setResizable(false);

            VBox containerCreateEnterprise = new VBox();

            Region region = new Region();
            VBox.setVgrow(region, Priority.ALWAYS);

            allBtns.setBtn1Action(e -> {
                openViewCreateEnt = false;
                stage.close();
            });

            containerCreateEnterprise.getChildren().addAll(newEnterpriseName, newPort, region, allBtns);
            containerCreateEnterprise.setSpacing(10);
            containerCreateEnterprise.setAlignment(Pos.CENTER);
            containerCreateEnterprise.setPadding(new Insets(10));
            Scene scene = new Scene(containerCreateEnterprise, 300, 150);

            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> openViewCreateEnt = false);

            openViewCreateEnt = true;
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
     * Gets the buttons for quit and create enterprise.
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
     * Sets the input field for the new port.
     *
     * @param newPort the input field for the new port
     */
    public void setNewPort(LabeledTextFieldHBox newPort) {
        this.newPort = newPort;
    }
}
