package theView.manage;


import javafx.scene.Scene;
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


public class AppWindowConnect {

    //  first window

    private LabeledComboHBox enterpriseName;

    private LabeledTextFieldHBox password;

    private Button BtnConnexion;

    private AllBtns firstWindowAllBtns;

    private Boolean openViewConnectEnt = false;

    private Stage stage;


    // second window

    public Stage getStage() {
        return stage;
    }

    public void setEnterpriseName(LabeledComboHBox enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public AppWindowConnect() {

        enterpriseName = new LabeledComboHBox("Enterprise Name", new String[]{"Enterprise name",
                "Enterprise 1", "Enterprise 2", "Enterprise 3"});
        password = new LabeledTextFieldHBox("Password", "");
        password.setPromptText("your password");
        password.setDisableToFalse();
        BtnConnexion = new Button("Connection");
        firstWindowAllBtns = new AllBtns("Quit", "Create Enterprise");
    }

    public void connectToEnterprise()
    {
        if (!openViewConnectEnt)
        {
            stage = new Stage();

            stage.setTitle("Manage App");

            stage.setResizable(false);

            VBox container = new VBox();

            HBox containerConnectORCreate = new HBox();
            containerConnectORCreate.setSpacing(10);
            containerConnectORCreate.setAlignment(Pos.CENTER);

            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            container.getChildren().addAll(enterpriseName, password,BtnConnexion, spacer, firstWindowAllBtns);

            container.setSpacing(10);

            container.setAlignment(Pos.CENTER);

            container.setPadding(new Insets(10));

            Scene scene = new Scene(container, 300, 200);

            stage.setScene(scene);

            openViewConnectEnt = true;

            stage.setOnCloseRequest(e->{
                openViewConnectEnt = false;
            });

            stage.show();
        }
    }

    public void setOpenViewToFalse()
    {
        openViewConnectEnt = false;
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

    public Button getCreateBtn(){return firstWindowAllBtns.getBtn2();}

    public static void PrintAlert(String title, String content)
    {
        Pointer.PrintAlert(title, content);
    }
}
