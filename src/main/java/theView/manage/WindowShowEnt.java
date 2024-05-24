package theView.manage;

import Sockets.ServersSocket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import theModel.DataSerialize;
import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;
import theModel.JobClasses.WorkHour;
import theModel.JobClasses.WorkHourEntry;
import theView.pointer.Pointer;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class WindowShowEnt {

    private static ObservableList<Employee> dataEmps;
    private static ObservableList<WorkHour> dataPointers;
    private static HBox contents;
    private static TableView<Employee> table;
    private static TableView<WorkHour> tablePointer;
    private static Button quitWindow;
    static Thread wserverThread;
    private static ServersSocket wserversSocket;


    public static Button getQuitWindow() {
        return quitWindow;
    }

    public static void showEnterpriseContent(DataSerialize d, Enterprise ent,Thread serverThread,
                                             ServersSocket serversSocket) {

        // Configurer la scène et la table
        Stage stage = new Stage();
        stage.setTitle(String.format("Enterprise '%s' management", ent.getEntname()));
        quitWindow = new Button("Quit window");

        wserverThread = serverThread;
        wserversSocket = serversSocket;
        quitWindowShowEvent(stage);

        MenuBar theMenu = new MenuBar();
        final Menu SeeEmp = new Menu("See all Employees");
        final Menu AllPointers =new Menu("All Pointers");

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

        seeEmps.setOnAction(e->{
            contents.getChildren().clear();
            contents.getChildren().addAll(seeTableAllEmp(d, ent));
        });


        savePointers.setOnAction(e->{
            contents.getChildren().clear();
            contents.getChildren().add(seeTableAllPointers(ent, true));
        });

        currentPointers.setOnAction(e->{
            contents.getChildren().clear();
            contents.getChildren().add(seeTableAllPointers(ent, false));
        });

        VBox container = new VBox();

        contents.getChildren().add(seeTableAllEmp(d, ent));
        container.getChildren().addAll(theMenu, contents, quitWindow);
        container.setSpacing(5);

        Scene scene = new Scene(container, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void quitWindowShowEvent(Stage stage)
    {
        quitWindow.setOnAction(e -> {
            System.out.println("Quit button pressed");
            try {
                if (wserverThread.isAlive())
                    wserversSocket.shutDown();
                stage.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private static VBox seeTableAllPointers(Enterprise ent, boolean seeAllPointers)
    {
        VBox EmpView = new VBox();

        // Créer une liste observable des employés
//        if (seeAllPointers)
//            loadDataAllPointers(ent);
//        else
//            loadDataDailyPointers(ent);

        tablePointer = new TableView<>();
        tablePointer.setEditable(true);

        // Configuration des colonnes

        TableColumn<WorkHour, String> dayOfWork = new TableColumn<>("Day Of work");
        TableColumn<WorkHour, String> uuidColumn = new TableColumn<>("UUID");
        TableColumn<WorkHour, String> empStartHour = new TableColumn<>("Starting Hour");
        TableColumn<WorkHour, String> empEndHour = new TableColumn<>("Ending Hour");

        dayOfWork.setCellValueFactory(new PropertyValueFactory<>("dateWork"));
        uuidColumn.setCellValueFactory(new PropertyValueFactory<>("empID"));
        empStartHour.setCellValueFactory(new PropertyValueFactory<>("hourStart"));
        empEndHour.setCellValueFactory(new PropertyValueFactory<>("hourEnd"));

        // Ajouter les colonnes au `TableView`
        tablePointer.getColumns().addAll(dayOfWork, uuidColumn, empStartHour, empEndHour); // workingHour

        tablePointer.setItems(dataPointers);

        EmpView.getChildren().addAll(tablePointer);

        EmpView.setSpacing(5);

        return EmpView;

    }


    private static VBox seeTableAllEmp(DataSerialize d, Enterprise ent) {

        VBox EmpView = new VBox();

        // Créer une liste observable des employés

        loadDataEmp(ent);

        table = new TableView<>();
        table.setEditable(true);

        // Configuration des colonnes
        TableColumn<Employee, String> uuidcolumn = new TableColumn<>("UUID");
        TableColumn<Employee, String> nameColumn = new TableColumn<>("FirstName");
        TableColumn<Employee, String> prenameColumn = new TableColumn<>("Prename");
        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        TableColumn<Employee, String> startingHourColumn = new TableColumn<>("startingHour");
        TableColumn<Employee, String> endingHourColumn = new TableColumn<>("EndingHour");

        uuidcolumn.setCellValueFactory(new PropertyValueFactory<>("uuid"));

        // Associer les colonnes aux propriétés correspondantes
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("empName"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> emp) {
                emp.getTableView().getItems().get(
                        emp.getTablePosition().getRow()).setEmpName(emp.getNewValue());
                try {
                    d.modifyEmpName(ent.getEntname(), emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        prenameColumn.setCellValueFactory(new PropertyValueFactory<>("empPrename"));
        prenameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        prenameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> emp) {
                emp.getTableView().getItems().get(
                        emp.getTablePosition().getRow()).setEmpPrename(emp.getNewValue());
                try {
                    d.modifyEmpPrename(ent.getEntname(), emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("emDep"));
        departmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departmentColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> emp) {
                emp.getTableView().getItems().get(
                        emp.getTablePosition().getRow()).setEmDep(emp.getNewValue());
                try {
                    d.modifyEmpDepartement(ent.getEntname(), emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        startingHourColumn.setCellValueFactory(new PropertyValueFactory<>("startingHour"));
        startingHourColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        startingHourColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> emp) {
                emp.getTableView().getItems().get(
                        emp.getTablePosition().getRow()).setStartingHour(emp.getNewValue());
                try {
                    d.modifyEmpStartingHour(ent.getEntname(), emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        endingHourColumn.setCellValueFactory(new PropertyValueFactory<>("endingHour"));
        endingHourColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        endingHourColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> emp) {
                emp.getTableView().getItems().get(
                        emp.getTablePosition().getRow()).setEndingHour(emp.getNewValue());
                try {
                    d.modifyEmpEndingHour(ent.getEntname(), emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });


        // Ajouter les colonnes au `TableView`
        table.getColumns().addAll(uuidcolumn,nameColumn, prenameColumn, departmentColumn,
                startingHourColumn, endingHourColumn); // workingHour

        table.setMinWidth(700);
        // Ajouter les données dans la table
        table.setItems(dataEmps);

        Pane detailsEmp = new Pane();

        table.setOnMouseClicked(event -> {
            // to print details about an employee
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                // todo : add new window for the employee's pointer
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                seeEmployeePointers(selectedItem);
            } else if (event.getButton() == MouseButton.SECONDARY) { // to remove an employee
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                //System.out.println("Clic droit sur : " + selectedItem);
                try {
                    d.removeEmployeeFromEnterprise(ent.getEntname(), selectedItem);
                    dataEmps.remove(selectedItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HBox tableAndDetails = new HBox();
        tableAndDetails.setSpacing(10);
        tableAndDetails.getChildren().addAll(table, detailsEmp);

        EmpView.getChildren().addAll(tableAndDetails, EmployeeBtnsCrud(ent, d));
        EmpView.setSpacing(5);
        return EmpView;

    }

//    private static void loadDataAllPointers(Enterprise ent)
//    {
//        ArrayList<WorkHour> array = new ArrayList<>();
//        Collection<ArrayList<WorkHour>> AllWorkHour = ent.getWorkHours().values();
//        for(ArrayList<WorkHour> CollectionWk : AllWorkHour)
//        {
//            for(WorkHour wk : CollectionWk)
//            {
//                array.add(new WorkHour(wk.getEmpID(), wk.getHourStart(), wk.getHourEnd(), wk.getDateWork()));
//            }
//        }
//
//        dataPointers = FXCollections.observableArrayList(array);
//    }
//
//    private static void loadDataDailyPointers(Enterprise ent)
//    {
//        ArrayList<WorkHour> array = new ArrayList<>();
//        Collection<ArrayList<WorkHour>> AllWorkHour = ent.getWorkHours().values();
//        for(ArrayList<WorkHour> CollectionWk : AllWorkHour)
//        {
//            for(WorkHour wk : CollectionWk)
//            {
//                if (wk.getDateWork().equals(LocalDate.now().toString()))
//                    array.add(new WorkHour(wk.getEmpID(), wk.getHourStart(), wk.getHourEnd(), wk.getDateWork()));
//            }
//        }
//
//        dataPointers = FXCollections.observableArrayList(array);
//    }

    private static void loadDataEmp(Enterprise ent)
    {
        ArrayList<Employee> array = new ArrayList<>();
        for (Employee emp : ent.getEmployees().values()) {

            array.add(new Employee(emp.getUuid(), emp.getEmpName(), emp.getEmpPrename(),
                    emp.getStartingHour(),
                    emp.getEndingHour(),
                    emp.getEmDep()
            ));
        }
        dataEmps = FXCollections.observableArrayList(array);
    }

    private static HBox EmployeeBtnsCrud(Enterprise ent, DataSerialize d)
    {
        HBox contentAddEmp = new HBox();

        Button addEmployeeBtn = new Button("addEmployee");

        addEmployeeBtn.setOnAction(e->{

            try
            {
                Employee newEmp = new Employee("FirstName",
                        "LastName","00:00",
                        "00:00", "unknown");

                d.addNewEmployeeToEnterprise(ent.getEntname(), newEmp);

                // updating the data of the tableView
                dataEmps.add(newEmp);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Pointer.PrintAlert("creation of new employee","employee added successfully !");
        });

        contentAddEmp.getChildren().addAll(addEmployeeBtn);

        contentAddEmp.setSpacing(2);

        return contentAddEmp;
    }

    private static void seeEmployeePointers(Employee emp)
    {
        Stage stage = new Stage();
        stage.setTitle(String.format("Employee (%s %s) pointers management", emp.getEmpName(),
                emp.getEmpPrename()));

        MenuBar theMenu = new MenuBar();
        final Menu SeeEmpDailyPointers = new Menu("See daily Pointers");
        final Menu AllEmpPointers =new Menu("See all Pointers");

        VBox empContents = new VBox();
        // menu click
        SeeEmpDailyPointers.setOnAction(event ->
        {
            empContents.getChildren().clear();
            // todo : load employee daily pointers
            empContents.getChildren().add(getEmployeePointers(emp, false));
        });

        AllEmpPointers.setOnAction(event ->
        {
            empContents.getChildren().clear();
            // todo : load all the employee pointers in the enterprise
            empContents.getChildren().add(getEmployeePointers(emp, true));

        });

        HBox btns = new HBox();

        Button addWorkHour = new Button("add workhour");

        VBox container = new VBox();
        container.getChildren().addAll(theMenu, empContents);

        Scene scene = new Scene(container, 700, 500);
        stage.setScene(scene);
        stage.show();

    }

    private static VBox getEmployeePointers(Employee emp, boolean allpointers)
    {
        VBox EmpView = new VBox();

        TableView<WorkHourEntry> employeeTableView = new TableView<>();
        employeeTableView.setEditable(true);

        TableColumn<WorkHourEntry, LocalDate> dateColumn = new TableColumn<>("Day Of work");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<WorkHourEntry, String> timesColumn = new TableColumn<>("Times");
        timesColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        employeeTableView.getColumns().add(dateColumn);
        employeeTableView.getColumns().add(timesColumn);

        /// Convert WorkHour data to WorkHourEntry list
        ObservableList<WorkHourEntry> workHourEntries = FXCollections.observableArrayList();
        HashMap<LocalDate, ArrayList<LocalTime>> pointing = emp.getWorkHour().getPointing();

        for (LocalDate date : pointing.keySet()) {
            if (allpointers)
            {
                for (LocalTime time : pointing.get(date))
                    workHourEntries.add(new WorkHourEntry(date, time));
            }
            else
            {
                for (LocalTime time : pointing.get(date)) {
                    if (date.equals(LocalDate.now()))
                        workHourEntries.add(new WorkHourEntry(date, time));
                }
            }
        }

        employeeTableView.setItems(workHourEntries);

        EmpView.getChildren().add(employeeTableView);

        EmpView.setOnMouseClicked(event->{
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                if(event.getButton() == MouseButton.SECONDARY)
                {
                    // todo : later
                    System.out.println("emp = " + emp + ", allpointers = " + allpointers);
                }
            }
        });

        return EmpView;
    }

}




