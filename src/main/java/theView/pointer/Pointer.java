package theView.pointer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;
import theView.utils.LabeledComboVBox;
import theView.utils.LabeledTextFieldHBox;

public class Pointer extends VBox {

    private ComponentDateHours DateHours;
    private LabeledComboHBox Connection;
    private LabeledComboVBox EmployeesName;
    private AllBtns checkInOut;
    private Button Quit;

    private LabeledTextFieldHBox port;
    private LabeledTextFieldHBox ip;
    public Pointer(EventHandler<ActionEvent> quit)
    {
        super();
        DateHours = new ComponentDateHours();
        Connection = new LabeledComboHBox("Connection", new String[]{"Enterprise1", "Enterprise2"});
        EmployeesName = new LabeledComboVBox("Employees", new String[]{"Employee1", "Employee2", "Employee3"});

        HBox ipPort = new HBox();
        port = new LabeledTextFieldHBox("Port : ", "1234", 50);
        ip = new LabeledTextFieldHBox("IP : ", "192.168.2.1");
        Region spacerOne = new Region();
        HBox.setHgrow(spacerOne, Priority.ALWAYS);
        ipPort.getChildren().addAll(ip, spacerOne, port);

        HBox buttons = new HBox();
        checkInOut = new AllBtns("Check In", "Check Out");
        Quit = new Button("Quit");
        Quit.setOnAction(quit);

        Region spacerTwo = new Region();
        HBox.setHgrow(spacerTwo, Priority.ALWAYS);
        buttons.getChildren().addAll(Quit,spacerTwo, checkInOut);

        Region spacerThree = new Region();
        VBox.setVgrow(spacerThree, Priority.ALWAYS);

        this.getChildren().addAll(DateHours, Connection,ipPort, EmployeesName,spacerThree, buttons);

        this.setPadding(new Insets(10));
        this.setSpacing(10);
    }

    public ComponentDateHours getDateHours() {
        return DateHours;
    }

    public LabeledComboHBox getConnection() {
        return Connection;
    }

    public LabeledComboVBox getEmployeesName() {
        return EmployeesName;
    }

    public AllBtns getCheckInOut() {
        return checkInOut;
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
}
