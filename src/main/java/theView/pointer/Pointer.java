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
import theModel.DataSerialize;
import theModel.ParameterSerialize;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;

import java.io.IOException;
import java.util.ArrayList;

public class Pointer extends VBox {

    private ComponentDateHours DateHours;
    private LabeledComboHBox Employees;
    private AllBtns logincheckInOut;
    private Button Quit;
    private ChangePointerConfig config;

    public Pointer(EventHandler<ActionEvent> quit)
    {
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
        buttons.getChildren().addAll(Quit,spacerTwo, logincheckInOut);

        Region spacerThree = new Region();
        VBox.setVgrow(spacerThree, Priority.ALWAYS);

        this.getChildren().addAll(DateHours, Employees,spacerThree, buttons);

        this.setPadding(new Insets(10));
        this.setSpacing(10);
    }

    public ComponentDateHours getDateHours() {
        return DateHours;
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

    public void saveNewConfigPointer() throws IOException {
        config.getNewIp().setLTFTextFieldValue(config.getNewIp().getLTFTextFieldValue());
        config.getNewPort().setLTFTextFieldValue(config.getNewPort().getLTFTextFieldValue());

        ParameterSerialize p = new ParameterSerialize();

        ArrayList<String> newParams = new ArrayList<>();
        newParams.add(config.getNewIp().getLTFTextFieldValue());
        newParams.add(config.getNewPort().getLTFTextFieldValue());

        p.saveData(newParams);
    }

    public static void PrintAlert(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("important information !");
        alert.setTitle(title);
        alert.setContentText(content);

        alert.show();
    }

    public ChangePointerConfig getConfig()
    {
        return config;
    }
}
