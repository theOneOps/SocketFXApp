package theView.manage.windowShowEnt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import theModel.DataSerialize;
import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;
import theView.pointer.Pointer;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeTableView {

    private static ObservableList<Employee> dataEmps;
    private static TableView<Employee> table;

    public static VBox seeTableAllEmp(DataSerialize d, Enterprise ent) {

        VBox EmpView = new VBox();

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
                if (UtilityWindowShowEnt.isValidTime(emp.getNewValue())) {
                    emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).setStartingHour(emp.getNewValue());
                    try {
                        d.modifyEmpStartingHour(ent.getEntname(), emp.getTableView().getItems().get(
                                emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else
                    Pointer.PrintAlert("modification failed ",
                            "the value you enter for the hour is not valid");

            }
        });

        endingHourColumn.setCellValueFactory(new PropertyValueFactory<>("endingHour"));
        endingHourColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        endingHourColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> emp) {
                if (UtilityWindowShowEnt.isValidTime(emp.getNewValue())) {
                    emp.getTableView().getItems().get(
                            emp.getTablePosition().getRow()).setEndingHour(emp.getNewValue());
                    try {
                        d.modifyEmpEndingHour(ent.getEntname(), emp.getTableView().getItems().get(
                                emp.getTablePosition().getRow()).getUuid(), emp.getNewValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else
                    Pointer.PrintAlert("modification failed ",
                            "the value you enter for the hour is not valid");

            }
        });

        table.getColumns().addAll(uuidcolumn, nameColumn, prenameColumn, departmentColumn,
                startingHourColumn, endingHourColumn);

        table.setMinWidth(700);
        table.setItems(dataEmps);

        Pane detailsEmp = new Pane();


        table.setOnMouseClicked(event -> {
            // show the employee workhours
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                try {
                    WindowShowEnt.seeEmployeePointers(ent.getEntPort(), d, selectedItem);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                // delete the employee we click on with the right click
            } else if (event.getButton() == MouseButton.SECONDARY) {
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                try {
                    d.removeEmployeeFromEnterprise(ent.getEntname(), selectedItem);
                    dataEmps.remove(selectedItem);
                    //PointerController.reloadEmployeesCombox(d.getEntByName(ent.getEntname()));
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

    private static void loadDataEmp(Enterprise ent) {
        ArrayList<Employee> array = new ArrayList<>();
        for (Employee emp : ent.getEmployees().values()) {
            array.add(new Employee(emp.getUuid(), emp.getEmpName(), emp.getEmpPrename(),
                    emp.getStartingHour(), emp.getEndingHour(), emp.getEmDep(), emp.getWorkHour()));
        }
        dataEmps = FXCollections.observableArrayList(array);
    }

    // add new employee
    private static HBox EmployeeBtnsCrud(Enterprise ent, DataSerialize d) {
        HBox contentAddEmp = new HBox();

        Button addEmployeeBtn = new Button("addEmployee");

        addEmployeeBtn.setOnAction(e -> {
            try {
                Employee newEmp = new Employee("FirstName",
                        "LastName", "00:00",
                        "00:00", "unknown");

                d.addNewEmployeeToEnterprise(ent.getEntname(), newEmp);
                dataEmps.add(newEmp);
                //PointerController.reloadEmployeesCombox(d.getEntByName(ent.getEntname()));

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Pointer.PrintAlert("creation of new employee", "employee added successfully !");
        });

        contentAddEmp.getChildren().addAll(addEmployeeBtn);

        contentAddEmp.setSpacing(2);

        return contentAddEmp;
    }
}
