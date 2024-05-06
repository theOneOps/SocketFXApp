package StartPoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import theController.ManageController;
import theModel.DataSerialize;
import theView.manage.AppManagement;

import java.io.IOException;

public class ManageMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, InterruptedException {
        AppManagement manageApp = new AppManagement();
        Scene scene = new Scene(manageApp.getAppWindowConnect(), 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage App");

        DataSerialize dataSerialize = new DataSerialize();
        ManageController manageController = new ManageController(manageApp, dataSerialize, primaryStage);

        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
