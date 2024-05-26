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

public class WindowCreateEnt {
    
    private LabeledTextFieldHBox newEnterpriseName;

    private LabeledTextFieldHBox newPasswd;

    private AllBtns allBtns;

    private LabeledTextFieldHBox newPort;

    private Boolean openViewCreateEnt = false;

    public WindowCreateEnt()
    {
        newEnterpriseName = new LabeledTextFieldHBox("Enterprise Name : ", "");
        newPasswd = new LabeledTextFieldHBox("Password : ", "");
        newEnterpriseName.setDisableToFalse();
        newPasswd.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Create");
        newPort = new LabeledTextFieldHBox("Port : ", "");
        newPort.setDisableToFalse();
    }

    public void createEnterprise()
    {
        if (!openViewCreateEnt)
        {
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

            containerCreateEnterprise.getChildren().addAll(newEnterpriseName, newPasswd,
                    newPort, region, allBtns);
            containerCreateEnterprise.setSpacing(10);
            containerCreateEnterprise.setAlignment(Pos.CENTER);
            containerCreateEnterprise.setPadding(new Insets(10));
            Scene scene = new Scene(containerCreateEnterprise, 300, 150);

            stage.setScene(scene);

            stage.show();

            stage.setOnCloseRequest(e->{
                openViewCreateEnt = false;
            });

            openViewCreateEnt = true;
        }
    }

    public LabeledTextFieldHBox getNewEnterpriseName() {
        return newEnterpriseName;
    }

    public LabeledTextFieldHBox getNewPasswd() {
        return newPasswd;
    }

    public AllBtns getAllBtns()
    {
        return allBtns;
    }

    public LabeledTextFieldHBox getNewPort() {
        return newPort;
    }

    public void setNewPort(LabeledTextFieldHBox newPort) {
        this.newPort = newPort;
    }
}
