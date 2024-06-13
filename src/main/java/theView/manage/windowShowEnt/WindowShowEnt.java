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

/**
 * The WindowShowEnt class provides the UI and functionality for viewing and managing
 * the details of an enterprise, including its employees and work hours.
 */
public class WindowShowEnt {

    private static ObservableList<Employee> dataEmps; // List of employees
    private static ObservableList<WorkHour> dataPointers; // List of work hours
    private static HBox contents; // Container for the main content
    private static TableView<Employee> table; // Table view for employees
    private static TableView<WorkHour> tablePointer; // Table view for work hours
    private static Button quitWindow; // Button to quit the window
    private static Thread wserverThread; // Thread for the server
    private static ServersSocket wserversSocket; // Server socket
    private static Boolean[] openViewCheckInPointers = {false, false}; // Flags to check if views are open
    private static AppWindowConnect connectEnt; // Connection window for the app
    private static Button welcomeWindowBtn; // Button to open the welcome window

    /**
     * Displays the enterprise management window.
     *
     * @param createEntClass the connection window class
     * @param d              the data serialization handler
     * @param ent            the enterprise
     * @param serverThread   the server thread
     * @param serversSocket  the server socket
     */
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

            welcomeWindowBtn = new Button("Welcome window");

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

            final RadioMenuItem seeEmps = new RadioMenuItem("See all employees");

            contents = new HBox();

            seeEmps.setOnAction(e -> {
                contents.getChildren().clear();
                contents.getChildren().addAll(EmployeeTableView.seeTableAllEmp(d, ent));
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

    /**
     * Sets the action for the quit button to close the window and stop the server.
     *
     * @param stage the stage of the window
     */
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

    /**
     * Displays the employee pointers management window.
     *
     * @param entPort the enterprise port
     * @param d       the data serialization handler
     * @param emp     the employee
     * @throws IOException            if an I/O error occurs during data loading
     * @throws ClassNotFoundException if the class for the serialized object cannot be found
     */
    public static void seeEmployeePointers(String entPort, DataSerialize d, Employee emp) throws IOException, ClassNotFoundException {
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
            empContents.getChildren().addAll(EmployeePointerView.getEmployeePointers(entPort, d, emp, false));

            seeDaily.setOnAction(event -> {
                empContents.getChildren().clear();
                try {
                    empContents.getChildren().addAll(EmployeePointerView.getEmployeePointers(entPort, d, emp, false));
                } catch (ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            });

            seeAll.setOnAction(event -> {
                empContents.getChildren().clear();
                try {
                    empContents.getChildren().addAll(EmployeePointerView.getEmployeePointers(entPort, d, emp, true));
                } catch (ClassNotFoundException|IOException e) {
                    throw new RuntimeException(e);
                }
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
