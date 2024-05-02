package theView.manage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;
import theView.utils.LabeledTextFieldHBox;

public class AppWindowConnect extends VBox {

    private LabeledComboHBox enterpriseName;
    private LabeledTextFieldHBox password;

    private Button Connexion;

    private int counterEnterprise = 1;

    public AllBtns getAllBtns() {
        return allBtns;
    }

    private AllBtns allBtns;

    public AppWindowConnect(EventHandler<ActionEvent> quit) {
        super();

        enterpriseName = new LabeledComboHBox("Enterprise Name", new String[]{"Enterprise name",
                "Enterprise 1", "Enterprise 2", "Enterprise 3"});
        password = new LabeledTextFieldHBox("Password", "your password");
        password.setDisableToFalse();

        Connexion = new Button("Connexion");

        HBox containerConnectORCreate = new HBox();

        allBtns = new AllBtns("Quit", "Create Enterprise");
        allBtns.setBtn1Action(quit);
        allBtns.setBtn2Action(e -> createEnterprise());
        containerConnectORCreate.setSpacing(10);
        containerConnectORCreate.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(enterpriseName, password,spacer, allBtns);

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

    public Button getConnexion() {
        return Connexion;
    }

    private void createEnterprise()
    {
        if (counterEnterprise > 0)
        {
            Stage stage = new Stage();

            stage.setTitle("Create Enterprise");

            stage.setResizable(false);

            VBox containerCreateEnterprise = new VBox();

            LabeledTextFieldHBox enterpriseName = new LabeledTextFieldHBox("Enterprise Name : ", "");
            LabeledTextFieldHBox port = new LabeledTextFieldHBox("Port : ", "");
            LabeledTextFieldHBox ip = new LabeledTextFieldHBox("IP : ", "");

            enterpriseName.setDisableToFalse();
            port.setDisableToFalse();
            ip.setDisableToFalse();

            Region region = new Region();
            VBox.setVgrow(region, Priority.ALWAYS);

            AllBtns allBtns = new AllBtns("Quit", "Create");

            allBtns.setBtn1Action(e -> stage.close());

            containerCreateEnterprise.getChildren().addAll(enterpriseName, port, ip, region, allBtns);
            containerCreateEnterprise.setSpacing(10);
            containerCreateEnterprise.setAlignment(Pos.CENTER);
            containerCreateEnterprise.setPadding(new Insets(10));
            Scene scene = new Scene(containerCreateEnterprise, 300, 170);

            stage.setScene(scene);

            stage.show();
            counterEnterprise--;
        }
    }
}
