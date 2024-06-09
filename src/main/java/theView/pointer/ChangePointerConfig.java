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

public class ChangePointerConfig {

    private static LabeledTextFieldHBox newPort;

    private static LabeledTextFieldHBox newIp;

    private static AllBtns allBtns;

    private static Boolean openViewConfigPointer = false;

    private static Stage stage;

    public LabeledTextFieldHBox getNewIp() {
        return newIp;
    }

    public void WindowConfigPointer()
    {
        newIp = new LabeledTextFieldHBox("IP", "");
        newIp.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Save Configuration");
        newPort = new LabeledTextFieldHBox("Port : ", "");
        newPort.setDisableToFalse();
    }

    public void openConfigPointer() throws IOException, ClassNotFoundException {
        if (!openViewConfigPointer)
        {
            ParameterSerialize p = new ParameterSerialize();
            ArrayList<String> params = p.loadData();

            newIp.setLTFTextFieldValue("");
            newIp.setPromptText("Enter Ip Number");
            newPort.setLTFTextFieldValue("");
            newPort.setPromptText("Enter Port Number");

            if (!params.isEmpty())
            {
                newIp.setLTFTextFieldValue(params.getFirst());
                newPort.setLTFTextFieldValue(params.get(1));
            }

            stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Change connection parameters");

            VBox container = new VBox();
            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            container.setSpacing(10);

            container.setAlignment(Pos.CENTER);

            container.setPadding(new Insets(10));

            container.getChildren().addAll(newIp, newPort,spacer, allBtns);

            Scene scene = new Scene(container, 400, 120);

            stage.setScene(scene);

            openViewConfigPointer = true;

            stage.setOnCloseRequest(e->{
                openViewConfigPointer = false;
            });

            allBtns.getBtn1().setOnAction(e->{
                stage.close();
                openViewConfigPointer = false;
            });

            stage.show();
        }
    }

    public static LabeledTextFieldHBox getNewPointerIp() {
        return newIp;
    }

    public AllBtns getAllBtns() {
        return allBtns;
    }

    public LabeledTextFieldHBox getNewPort() {
        return newPort;
    }

    public Boolean getOpenViewConfigEnt() {
        return openViewConfigPointer;
    }

    public Button getQuit()
    {
        return allBtns.getBtn1();
    }

    public Button getSaveConfigBtn()
    {
        return allBtns.getBtn2();
    }
}
