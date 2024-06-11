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

    private LabeledComboHBox enterpriseName;

    private Button BtnConnexion;

    private AllBtns firstWindowAllBtns;

    private Boolean openViewConnectEnt = false;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public AppWindowConnect()
    {
        enterpriseName = new LabeledComboHBox("Enterprise Name", new String[]{"Enterprise name",
                "Enterprise 1", "Enterprise 2", "Enterprise 3"});
        BtnConnexion = new Button("Connection");
        firstWindowAllBtns = new AllBtns("Quit","Config", "Create Enterprise");
    }

    public void connectToEnterprise()
    {
        if (!openViewConnectEnt)
        {
            stage = new Stage();

            stage.setTitle("Manage App");

            stage.setResizable(false);

            VBox container = new VBox();

            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            container.getChildren().addAll(enterpriseName, BtnConnexion, spacer, firstWindowAllBtns);

            container.setSpacing(10);

            container.setAlignment(Pos.CENTER);

            container.setPadding(new Insets(10));

            Scene scene = new Scene(container, 300, 150);

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

    public Button getBtnConnexion() {
        return BtnConnexion;
    }

    public Button getQuitBtn() {
        return firstWindowAllBtns.getBtn1();
    }

    public Button getConfigBtn(){
        return firstWindowAllBtns.getBtn2();
    }

    public Button getCreateBtn(){return firstWindowAllBtns.getBtn3();}

    public static void PrintAlert(String title, String content)
    {
        Pointer.PrintAlert(title, content);
    }
}
