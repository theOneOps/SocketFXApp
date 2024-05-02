package StartPoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import theView.manage.AppWindowConnect;

public class ManageMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AppWindowConnect manageApp = new AppWindowConnect(setAction -> {
            primaryStage.close();
        });
        Scene scene = new Scene(manageApp, 300, 150);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage App");

        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
