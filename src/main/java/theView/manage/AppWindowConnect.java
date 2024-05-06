package theView.manage;


import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import theModel.JobClasses.Enterprise;
import theView.pointer.Pointer;
import theView.utils.AllBtns;
import theView.utils.LabeledComboHBox;
import theView.utils.LabeledTextFieldHBox;


public class AppWindowConnect extends VBox {

    //  first window

    private LabeledComboHBox enterpriseName;

    private LabeledTextFieldHBox password;

    private Button BtnConnexion;

    private AllBtns firstWindowAllBtns;

    // second window



    public void setEnterpriseName(LabeledComboHBox enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public AppWindowConnect() {
        super();

        enterpriseName = new LabeledComboHBox("Enterprise Name", new String[]{"Enterprise name",
                "Enterprise 1", "Enterprise 2", "Enterprise 3"});


        password = new LabeledTextFieldHBox("Password", "");
        password.setPromptText("your password");


        password.setDisableToFalse();


        BtnConnexion = new Button("Connection");

        HBox containerConnectORCreate = new HBox();
        firstWindowAllBtns = new AllBtns("Quit", "Create Enterprise");
        containerConnectORCreate.setSpacing(10);
        containerConnectORCreate.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);


        this.getChildren().addAll(enterpriseName, password,BtnConnexion, spacer, firstWindowAllBtns);

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

    public Button getCreateBtn(){return firstWindowAllBtns.getBtn2();}

    public static void PrintAlert(String title, String content)
    {
        Pointer.PrintAlert(title, content);
    }
}
