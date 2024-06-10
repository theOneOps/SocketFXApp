package theView.pointer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import theModel.ParameterSerialize;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Pointer class is a JavaFX component that includes date and time display, employee selection, and buttons for configuration and check-in/out.
 */
public class Pointer extends VBox {

    private ComponentDateHours DateHours;   // Component to display the current date and time
    private LabeledComboHBox Employees;     // Dropdown component for selecting employees
    private AllBtns logincheckInOut;        // Buttons for configuration and check-in/out actions
    private Button Quit;                    // Button to quit the application
    private ChangePointerConfig config;     // Configuration component for changing pointer settings

    /**
     * Constructor for the Pointer class.
     *
     * @param quit EventHandler for the quit button action.
     */
    public Pointer(EventHandler<ActionEvent> quit) {
        super();
        config = new ChangePointerConfig();
        config.WindowConfigPointer();
        DateHours = new ComponentDateHours();
        Employees = new LabeledComboHBox("Employees", new String[]{"choose your name"});

        HBox buttons = new HBox();
        logincheckInOut = new AllBtns("Config", "Check In/Out");
        Quit = new Button("Quit");

        Region spacerTwo = new Region();
        HBox.setHgrow(spacerTwo, Priority.ALWAYS);
        buttons.getChildren().addAll(Quit, spacerTwo, logincheckInOut);

        Region spacerThree = new Region();
        VBox.setVgrow(spacerThree, Priority.ALWAYS);

        this.getChildren().addAll(DateHours, Employees, spacerThree, buttons);

        this.setPadding(new Insets(10));
        this.setSpacing(10);
    }

    /**
     * Gets the ComponentDateHours instance.
     *
     * @return the ComponentDateHours instance
     */
    public ComponentDateHours getDateHours() {
        return DateHours;
    }

    /**
     * Gets the LabeledComboHBox for employees.
     *
     * @return the LabeledComboHBox for employees
     */
    public LabeledComboHBox getEmployees() {
        return Employees;
    }

    /**
     * Gets the AllBtns instance for login and check-in/out buttons.
     *
     * @return the AllBtns instance
     */
    public AllBtns getLoginCheckInOut() {
        return logincheckInOut;
    }

    /**
     * Gets the quit button.
     *
     * @return the quit button
     */
    public Button getQuit() {
        return Quit;
    }

    /**
     * Saves the new configuration pointer settings.
     *
     * @throws IOException if there is an issue with IO operations
     */
    public void saveNewConfigPointer() throws IOException {
        // Update the new IP and port values from the configuration input fields
        config.getNewIp().setLTFTextFieldValue(config.getNewIp().getLTFTextFieldValue());
        config.getNewPort().setLTFTextFieldValue(config.getNewPort().getLTFTextFieldValue());

        ParameterSerialize p = new ParameterSerialize();

        // Collect the new parameters
        ArrayList<String> newParams = new ArrayList<>();
        newParams.add(config.getNewIp().getLTFTextFieldValue());
        newParams.add(config.getNewPort().getLTFTextFieldValue());

        // Save the new parameters
        p.saveData(newParams);
    }

    /**
     * Displays an alert with the specified title and content.
     *
     * @param title   the title of the alert
     * @param content the content of the alert
     */
    public static void PrintAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Important information!");
        alert.setTitle(title);
        alert.setContentText(content);

        alert.show();
    }

    /**
     * Gets the ChangePointerConfig instance.
     *
     * @return the ChangePointerConfig instance
     */
    public ChangePointerConfig getConfig() {
        return config;
    }
}
