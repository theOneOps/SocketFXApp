package StartPoint;

import javafx.application.Application;
import javafx.stage.Stage;
import theController.ManageController;
import theModel.DataSerialize;
import theView.manage.AppManagement;

import java.io.IOException;

/**
 * The ManageMain class serves as the entry point for the JavaFX application.
 * It initializes the application components and starts the JavaFX stage.
 */
public class ManageMain extends Application {

    /**
     * The main method launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is called after the JavaFX application is launched.
     * It initializes the application management view and controller.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException            if an I/O error occurs during initialization
     * @throws ClassNotFoundException if the class for the serialized object cannot be found
     * @throws InterruptedException   if the initialization is interrupted
     */
    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, InterruptedException {
        // Initialize the application management view
        AppManagement manageApp = new AppManagement();

        // Initialize the data serialization handler
        DataSerialize dataSerialize = new DataSerialize();

        // Initialize the application controller
        ManageController manageController = new ManageController(manageApp, dataSerialize);
    }
}
