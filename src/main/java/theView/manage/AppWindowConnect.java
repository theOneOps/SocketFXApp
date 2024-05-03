package theView.manage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import theView.pointer.Pointer;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;
import theView.utils.LabeledTextFieldHBox;

import java.util.Arrays;

public class AppWindowConnect extends VBox {

    //  first window

    private LabeledComboHBox enterpriseName;

    private LabeledTextFieldHBox ip;

    private LabeledTextFieldHBox port;

    private LabeledTextFieldHBox password;

    private Button BtnConnexion;

    private AllBtns firstWindowAllBtns;

    // second window

    private int counterEnterprise = 1;

    private LabeledTextFieldHBox newEnterpriseName;

    private LabeledTextFieldHBox newPasswd;

    private AllBtns secondWindowAllBtns;

    public void setEnterpriseName(LabeledComboHBox enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public AppWindowConnect() {
        super();

        enterpriseName = new LabeledComboHBox("Enterprise Name", new String[]{"Enterprise name",
                "Enterprise 1", "Enterprise 2", "Enterprise 3"});

        HBox IpPortBox = new HBox();
        ip = new LabeledTextFieldHBox("Ip", "");
        port = new LabeledTextFieldHBox("Port", "");
        IpPortBox.setSpacing(5);
        IpPortBox.getChildren().addAll(ip, port);

        password = new LabeledTextFieldHBox("Password", "");
        password.setPromptText("your password");
        ip.setPromptText("set an IP");
        port.setPromptText("set a port");

        password.setDisableToFalse();
        ip.setDisableToFalse();
        port.setDisableToFalse();

        BtnConnexion = new Button("Connection");

        HBox containerConnectORCreate = new HBox();
        firstWindowAllBtns = new AllBtns("Quit", "Create Enterprise");
        firstWindowAllBtns.setBtn2Action(e -> createEnterprise());
        containerConnectORCreate.setSpacing(10);
        containerConnectORCreate.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);


        this.getChildren().addAll(enterpriseName,IpPortBox, password,BtnConnexion, spacer, firstWindowAllBtns);

        this.setSpacing(10);

        this.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(10));
    }

    public LabeledComboHBox getEnterpriseName() {
        return enterpriseName;
    }

    public LabeledTextFieldHBox getPassword() {
        return password;
    }

    public Button getBtnConnexion() {
        return BtnConnexion;
    }

    public AllBtns getFirstWindowAllBtns() {
        return firstWindowAllBtns;
    }

    public Button getQuitBtn() {
        return firstWindowAllBtns.getBtn1();
    }

    public LabeledTextFieldHBox getIp() {
        return ip;
    }

    public LabeledTextFieldHBox getPort() {
        return port;
    }

    private void createEnterprise()
    {
        if (counterEnterprise > 0)
        {
            Stage stage = new Stage();

            stage.setTitle("Create Enterprise");

            stage.setResizable(false);

            VBox containerCreateEnterprise = new VBox();

            newEnterpriseName = new LabeledTextFieldHBox("Enterprise Name : ", "");
            newPasswd = new LabeledTextFieldHBox("Password : ", "");

            newEnterpriseName.setDisableToFalse();
            newPasswd.setDisableToFalse();

            Region region = new Region();
            VBox.setVgrow(region, Priority.ALWAYS);

            secondWindowAllBtns = new AllBtns("Quit", "Create");

            secondWindowAllBtns.setBtn1Action(e -> stage.close());

            containerCreateEnterprise.getChildren().addAll(newEnterpriseName,newPasswd, region, secondWindowAllBtns);
            containerCreateEnterprise.setSpacing(10);
            containerCreateEnterprise.setAlignment(Pos.CENTER);
            containerCreateEnterprise.setPadding(new Insets(10));
            Scene scene = new Scene(containerCreateEnterprise, 300, 150);

            stage.setScene(scene);

            stage.show();
            counterEnterprise--;
        }
    }

    public void setQuitAction(EventHandler<ActionEvent> quitAction) {
        secondWindowAllBtns.setBtn1Action(quitAction);
    }

    public void setCreateEnterpriseAction(EventHandler<ActionEvent> createEnterpriseAction) {
        secondWindowAllBtns.setBtn2Action(createEnterpriseAction);
    }

    public static void PrintAlert(String title, String content)
    {
        Pointer.PrintAlert(title, content);
    }
}
