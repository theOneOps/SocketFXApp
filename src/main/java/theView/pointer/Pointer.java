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
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;
import theView.utils.LabeledTextFieldHBox;

public class Pointer extends VBox {

    private ComponentDateHours DateHours;
    private LabeledComboHBox Connection;
    private LabeledComboHBox Employees;
    private AllBtns logincheckInOut;
    private Button Quit;

    private LabeledTextFieldHBox port;
    private LabeledTextFieldHBox ip;

    public Pointer(EventHandler<ActionEvent> quit)
    {
        super();
        DateHours = new ComponentDateHours();
        //Connection = new LabeledComboHBox("Connection", new String[]{"Enterprise1", "Enterprise2"});
        Employees = new LabeledComboHBox("Employees", new String[]{"choose your name"});

        HBox ipPort = new HBox();
        port = new LabeledTextFieldHBox("Port : ", "", 100);
        port.setPromptText("Enter Port Number");
        ip = new LabeledTextFieldHBox("IP : ", "", 100);
        ip.setPromptText("Enter IP Address");
        Region spacerOne = new Region();
        HBox.setHgrow(spacerOne, Priority.ALWAYS);
        ipPort.getChildren().addAll(ip, spacerOne, port);
        ip.setDisableToFalse();
        port.setDisableToFalse();

        HBox buttons = new HBox();
        logincheckInOut = new AllBtns("Connection", "Check In/Out");
        Quit = new Button("Quit");

        Region spacerTwo = new Region();
        HBox.setHgrow(spacerTwo, Priority.ALWAYS);
        buttons.getChildren().addAll(Quit,spacerTwo, logincheckInOut);

        Region spacerThree = new Region();
        VBox.setVgrow(spacerThree, Priority.ALWAYS);

        this.getChildren().addAll(DateHours,ipPort, Employees,spacerThree, buttons);

        this.setPadding(new Insets(10));
        this.setSpacing(10);
    }

    public ComponentDateHours getDateHours() {
        return DateHours;
    }

    public LabeledComboHBox getConnection() {
        return Connection;
    }

    public LabeledComboHBox getEmployees() {
        return Employees;
    }

    public AllBtns getLoginCheckInOut() {
        return logincheckInOut;
    }

    public Button getQuit() {
        return Quit;
    }

    public LabeledTextFieldHBox getPort() {
        return port;
    }

    public LabeledTextFieldHBox getIp() {
        return ip;
    }

    public static void PrintAlert(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("important information !");
        alert.setTitle(title);
        alert.setContentText(content);

        alert.show();
    }
}
