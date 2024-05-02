module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    //opens theView to javafx.fxml;
    //exports theView;

    // opens theModel to javafx.fxml;
    // exports theModel;

    // opens theController to javafx.fxml;
    // exports theController;

    opens StartPoint to javafx.fxml;
    exports StartPoint;
}