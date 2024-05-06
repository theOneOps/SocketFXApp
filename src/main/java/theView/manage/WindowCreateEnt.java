package theView.manage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    public WindowCreateEnt()
    {
        newEnterpriseName = new LabeledTextFieldHBox("Enterprise Name : ", "");
        newPasswd = new LabeledTextFieldHBox("Password : ", "");
        newEnterpriseName.setDisableToFalse();
        newPasswd.setDisableToFalse();
        allBtns = new AllBtns("Quit", "Create");
    }

    public void createEnterprise()
    {
            Stage stage = new Stage();

            stage.setTitle("Create Enterprise");

            stage.setResizable(false);

            VBox containerCreateEnterprise = new VBox();

            newEnterpriseName.setDisableToFalse();
            newPasswd.setDisableToFalse();

            Region region = new Region();
            VBox.setVgrow(region, Priority.ALWAYS);

            allBtns.setBtn1Action(e -> stage.close());

            containerCreateEnterprise.getChildren().addAll(newEnterpriseName,newPasswd, region, allBtns);
            containerCreateEnterprise.setSpacing(10);
            containerCreateEnterprise.setAlignment(Pos.CENTER);
            containerCreateEnterprise.setPadding(new Insets(10));
            Scene scene = new Scene(containerCreateEnterprise, 300, 150);

            stage.setScene(scene);

            stage.show();
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
}
