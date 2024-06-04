module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens theView.manage to javafx.fxml;
    opens theView.pointer to javafx.fxml;
    opens theView.utils to javafx.fxml;

    exports theView.manage;
    exports theView.pointer;
    exports theView.utils;


    exports theModel;
    opens theModel.JobClasses to javafx.base;
    exports theModel.JobClasses;


    opens theController to javafx.fxml;
    exports theController;

    opens StartPoint to javafx.fxml;
    exports StartPoint;
    opens theModel to javafx.base, javafx.fxml;
    exports theView.manage.windowShowEnt;
    opens theView.manage.windowShowEnt to javafx.fxml;
}