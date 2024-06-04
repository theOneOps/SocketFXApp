package theView.manage.windowShowEnt;

import Sockets.ServersSocket;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import theModel.DataSerialize;
import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;
import theModel.JobClasses.WorkHour;
import theView.manage.AppWindowConnect;

import java.io.IOException;

public class WindowShowEnt {

    private static ObservableList<Employee> dataEmps;
    private static ObservableList<WorkHour> dataPointers;
    private static HBox contents;
    private static TableView<Employee> table;
    private static TableView<WorkHour> tablePointer;
    private static Button quitWindow;
    static Thread wserverThread;
    private static ServersSocket wserversSocket;
    private static Boolean[] openViewCheckInPointers = {false, false};
    private static AppWindowConnect connectEnt;
    private static Button welcomeWindowBtn;

    public static void showEnterpriseContent(AppWindowConnect createEntClass, DataSerialize d,
                                             Enterprise ent, Thread serverThread,
                                             ServersSocket serversSocket) {
        if (!openViewCheckInPointers[0]) {
            Stage stage = new Stage();

            connectEnt = createEntClass;

            stage.setOnCloseRequest(e -> {
                if (wserverThread.isAlive()) {
                    try {
                        wserversSocket.shutDown();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                openViewCheckInPointers[0] = false;
            });
            stage.setTitle(String.format("Enterprise '%s' management", ent.getEntname()));
            quitWindow = new Button("Quit window");

            welcomeWindowBtn = new Button("welcome window");

            welcomeWindowBtn.setOnAction(e -> {
                try {
                    if (wserverThread.isAlive())
                        wserversSocket.shutDown();
                    connectEnt.connectToEnterprise();
                    stage.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                openViewCheckInPointers[0] = false;
            });

            wserverThread = serverThread;
            wserversSocket = serversSocket;
            quitWindowShowEvent(stage);

            MenuBar theMenu = new MenuBar();
            final Menu SeeEmp = new Menu("See all Employees");
            final Menu AllPointers = new Menu("All Pointers");

            final RadioMenuItem seeEmps = new RadioMenuItem("See all employees");
            final ToggleGroup toggleGroupOne = new ToggleGroup();
            seeEmps.setToggleGroup(toggleGroupOne);

            final RadioMenuItem savePointers = new RadioMenuItem("All save pointers");
            final RadioMenuItem currentPointers = new RadioMenuItem("All current pointers");

            final ToggleGroup toggleGroupTwo = new ToggleGroup();
            savePointers.setToggleGroup(toggleGroupTwo);
            currentPointers.setToggleGroup(toggleGroupTwo);

            AllPointers.getItems().addAll(savePointers, currentPointers);
            SeeEmp.getItems().add(seeEmps);
            theMenu.getMenus().addAll(SeeEmp, AllPointers);

            contents = new HBox();

            seeEmps.setOnAction(e -> {
                contents.getChildren().clear();
                contents.getChildren().addAll(EmployeeTableView.seeTableAllEmp(d, ent));
            });

            savePointers.setOnAction(e -> {
                contents.getChildren().clear();
                contents.getChildren().add(WorkHourTableView.seeTableAllPointers(ent, true));
            });

            currentPointers.setOnAction(e -> {
                contents.getChildren().clear();
                contents.getChildren().add(WorkHourTableView.seeTableAllPointers(ent, false));
            });

            VBox container = new VBox();

            HBox btnslayer = new HBox();

            btnslayer.getChildren().addAll(quitWindow, welcomeWindowBtn);

            btnslayer.setSpacing(10);

            contents.getChildren().add(EmployeeTableView.seeTableAllEmp(d, ent));
            container.getChildren().addAll(theMenu, contents, btnslayer);
            container.setSpacing(5);

            Scene scene = new Scene(container, 700, 500);
            stage.setScene(scene);
            stage.show();

            openViewCheckInPointers[0] = true;
        }
    }

    public static void quitWindowShowEvent(Stage stage) {
        quitWindow.setOnAction(e -> {
            System.out.println("Quit button pressed");
            try {
                if (wserverThread.isAlive())
                    wserversSocket.shutDown();
                stage.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            openViewCheckInPointers[0] = false;
        });
    }

    public static void seeEmployeePointers(String entName, DataSerialize d, Employee emp) {
        if (!openViewCheckInPointers[1]) {
            Stage stage = new Stage();
            stage.setTitle(String.format("Employee (%s %s) pointers management", emp.getEmpName(),
                    emp.getEmpPrename()));

            MenuBar theMenu = new MenuBar();
            Menu SeeEmpDailyPointers = new Menu("See daily Pointers");
            Menu AllEmpPointers = new Menu("See all Pointers");

            MenuItem seeDaily = new MenuItem("See daily Pointers");
            MenuItem seeAll = new MenuItem("See all Pointers");

            SeeEmpDailyPointers.getItems().add(seeDaily);
            AllEmpPointers.getItems().add(seeAll);
            theMenu.getMenus().addAll(SeeEmpDailyPointers, AllEmpPointers);

            VBox empContents = new VBox();
            empContents.getChildren().addAll(EmployeePointerView.getEmployeePointers(entName, d, emp, false));

            seeDaily.setOnAction(event -> {
                empContents.getChildren().clear();
                empContents.getChildren().addAll(EmployeePointerView.getEmployeePointers(entName, d, emp, false));
            });

            seeAll.setOnAction(event -> {
                empContents.getChildren().clear();
                empContents.getChildren().addAll(EmployeePointerView.getEmployeePointers(entName, d, emp, true));
            });

            Button quitWindow = new Button("Quit");
            quitWindow.setOnAction(e -> {
                openViewCheckInPointers[1] = false;
                stage.close();
            });

            VBox container = new VBox();
            container.setSpacing(10);
            container.getChildren().addAll(theMenu, empContents, quitWindow);

            Scene scene = new Scene(container, 350, 250);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> openViewCheckInPointers[1] = false);

            openViewCheckInPointers[1] = true;
        }
    }
}
