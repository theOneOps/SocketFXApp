package theView.manage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import theView.pointer.Pointer;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;

/**
 * The AppWindowConnect class represents the connection window for the application.
 * It provides the UI elements and functionality to connect to an enterprise.
 * Its attributes are:
 * <p>
 *     <ul>
 *         <li>{@code enterpriseName} LabeledComboHBox : the dropdown for selecting an enterprise name</li>
 *         <li>{@code BtnConnexion} Button : the button to initiate connection</li>
 *         <li>{@code firstWindowAllBtns} AllBtns : the buttons for quit, config, and create enterprise</li>
 *         <li>{@code openViewConnectEnt} Boolean : flag to check if the connection window is open</li>
 * </p>
 *
 * @see LabeledComboHBox
 * @see Button
 * @see AllBtns
 *
 */
public class AppWindowConnect {

    private LabeledComboHBox enterpriseName; // Dropdown for selecting an enterprise name
    private Button BtnConnexion; // Button to initiate connection
    private AllBtns firstWindowAllBtns; // Buttons for quit, config, and create enterprise
    private Boolean openViewConnectEnt = false; // Flag to check if the connection window is open
    private Stage stage; // The stage for the connection window

    /**
     * Constructs an AppWindowConnect instance and initializes the UI components.
     */
    public AppWindowConnect() {
        enterpriseName = new LabeledComboHBox("Enterprise Name", new String[]{"Enterprise name",
                "Enterprise 1", "Enterprise 2", "Enterprise 3"});
        BtnConnexion = new Button("Connection");
        firstWindowAllBtns = new AllBtns("Quit", "Config", "Create Enterprise");
    }

    /**
     * Opens the connection window if it is not already open.
     */
    public void connectToEnterprise() {
        if (!openViewConnectEnt) {
            stage = new Stage();
            stage.setTitle("Manage App");
            stage.setResizable(false);

            VBox container = new VBox();

            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            container.getChildren().addAll(enterpriseName, BtnConnexion, spacer, firstWindowAllBtns);
            container.setSpacing(10);
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(10));

            Scene scene = new Scene(container, 300, 150);
            stage.setScene(scene);

            openViewConnectEnt = true;

            stage.setOnCloseRequest(e -> openViewConnectEnt = false);

            stage.show();
        }
    }

    /**
     * Sets the open view flag to false, indicating that the connection window is closed.
     */
    public void setOpenViewToFalse() {
        openViewConnectEnt = false;
    }

    /**
     * Gets the LabeledComboHBox for selecting the enterprise name.
     *
     * @return the enterprise name dropdown
     */
    public LabeledComboHBox getEnterpriseName() {
        return enterpriseName;
    }

    /**
     * Gets the connection button.
     *
     * @return the connection button
     */
    public Button getBtnConnexion() {
        return BtnConnexion;
    }

    /**
     * Gets the AllBtns instance containing the quit, config, and create enterprise buttons.
     *
     * @return the AllBtns instance
     */
    public AllBtns getFirstWindowAllBtns() {
        return firstWindowAllBtns;
    }

    /**
     * Gets the quit button.
     *
     * @return the quit button
     */
    public Button getQuitBtn() {
        return firstWindowAllBtns.getBtn1();
    }

    /**
     * Gets the config button.
     *
     * @return the config button
     */
    public Button getConfigBtn() {
        return firstWindowAllBtns.getBtn2();
    }

    /**
     * Gets the create enterprise button.
     *
     * @return the create enterprise button
     */
    public Button getCreateBtn() {
        return firstWindowAllBtns.getBtn3();
    }

    /**
     * Displays an alert with the specified title and content.
     *
     * @param title   the title of the alert
     * @param content the content of the alert
     */
    public static void PrintAlert(String title, String content) {
        Pointer.PrintAlert(title, content);
    }

    /**
     * Gets the stage of the connection window.
     *
     * @return the stage of the connection window
     */
    public Stage getStage() {
        return stage;
    }
}
