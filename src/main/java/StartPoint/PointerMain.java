package StartPoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import theController.PointerController;
import theModel.DataSerialize;
import theView.pointer.Pointer;

public class PointerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pointer pointer = new Pointer(setOnAction -> {
            primaryStage.close();
        });

        DataSerialize dataSerialize = new DataSerialize();
        PointerController pointerController = new PointerController(pointer, dataSerialize);
        Scene scene = new Scene(pointer, 400, 250); // create a scene with a specific width and height
        primaryStage.setTitle("Time tracker Emulator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
