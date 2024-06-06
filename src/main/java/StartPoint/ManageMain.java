package StartPoint;

import javafx.application.Application;
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

        DataSerialize dataSerialize = new DataSerialize();
        ManageController manageController = new ManageController(manageApp, dataSerialize);
    }
}
