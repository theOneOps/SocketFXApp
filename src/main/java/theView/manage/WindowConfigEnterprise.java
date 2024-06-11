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

public class WindowConfigEnterprise {

    private LabeledTextFieldHBox newEnterpriseName;

    private LabeledTextFieldHBox newPort;

    private AllBtns allBtns;

    private Boolean openViewConfigEnt = false;

    private Stage stage;

    public WindowConfigEnterprise()
    {
        newEnterpriseName = new LabeledTextFieldHBox("new Enterprise Name : ", "");
        newEnterpriseName.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Save Configuration");
        newPort = new LabeledTextFieldHBox("Port : ", "");
        newPort.setDisableToFalse();
    }

    public void configEnterprise(Enterprise oldEnt)
    {
        if (!openViewConfigEnt)
        {
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

            container.getChildren().addAll(newEnterpriseName, newPort,spacer, allBtns);

            Scene scene = new Scene(container, 400, 120);

            stage.setScene(scene);

            openViewConfigEnt = true;

            stage.setOnCloseRequest(e->{
                openViewConfigEnt = false;
            });

            allBtns.getBtn1().setOnAction(e->{
                stage.close();
                openViewConfigEnt = false;
            });

            stage.show();
        }
    }

    public LabeledTextFieldHBox getNewEnterpriseName() {
        return newEnterpriseName;
    }

    public LabeledTextFieldHBox getNewPort() {
        return newPort;
    }

    public Button getSaveConfigBtn()
    {
        return allBtns.getBtn2();
    }
}
