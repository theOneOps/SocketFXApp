package theView.manage.windowShowEnt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import theModel.DataSerialize;
import theModel.JobClasses.Employee;
import theModel.JobClasses.WorkHourEntry;
import theView.pointer.Pointer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeePointerView {

    public static VBox getEmployeePointers(String entName, DataSerialize d, Employee emp, boolean allpointers) {
        VBox EmpView = new VBox();

        TableView<WorkHourEntry> employeeTableView = new TableView<>();
        employeeTableView.setEditable(true);

        TableColumn<WorkHourEntry, String> dateColumn = getWorkHourEntryStringTableColumn(entName, d, emp);
        TableColumn<WorkHourEntry, String> timesColumn = getHourEntryStringTableColumn(entName, d, emp);

        ObservableList<WorkHourEntry> workHourEntries = FXCollections.observableArrayList();
        HashMap<LocalDate, ArrayList<LocalTime>> pointing = emp.getWorkHour().getPointing();

        Button addWorkHour = new Button("add workhour");

        addWorkHour.setOnAction(e -> {
            try {
                WorkHourEntry newEntry = new WorkHourEntry(LocalDate.now(), LocalTime.parse("00:00"));
                d.addNewWorkHour(entName, emp.getUuid(), newEntry.getDate(), newEntry.getTime());
                workHourEntries.add(newEntry);
                Pointer.PrintAlert(String.format("creation of a new workhour for employee %s", emp.getEmpName()),
                        "workhour added successfully !");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        for (LocalDate date : pointing.keySet()) {
            if (allpointers) {
                for (LocalTime time : pointing.get(date)) {
                    WorkHourEntry entry = new WorkHourEntry(date, time);
                    workHourEntries.add(entry);
                }
            } else {
                for (LocalTime time : pointing.get(date)) {
                    if (date.equals(LocalDate.now())) {
                        WorkHourEntry entry = new WorkHourEntry(date, time);
                        workHourEntries.add(entry);
                    }
                }
            }
        }

        employeeTableView.getColumns().addAll(dateColumn, timesColumn);
        employeeTableView.setItems(workHourEntries);

        EmpView.getChildren().addAll(employeeTableView, addWorkHour);

        EmpView.setSpacing(10);

        employeeTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.SECONDARY) {
                WorkHourEntry selectedItem = employeeTableView.getSelectionModel().getSelectedItem();
                try {
                    d.removeWorkHour(entName, emp.getUuid(), selectedItem.getDate(), selectedItem.getTime());
                    workHourEntries.remove(selectedItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return EmpView;
    }

    private static TableColumn<WorkHourEntry, String> getHourEntryStringTableColumn(String entName, DataSerialize d, Employee emp) {
        TableColumn<WorkHourEntry, String> timesColumn = new TableColumn<>("Times");
        timesColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        timesColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkHourEntry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkHourEntry, String> event) {
                System.out.println("change of the time's employee workhour !");
                WorkHourEntry entry = event.getRowValue();
                String newHour = event.getNewValue();
                if (UtilityWindowShowEnt.isValidTime(newHour)) {
                    try {
                        d.modifyTimeWorkHour(entName, emp.getUuid(),
                                entry.getDate(), entry.getTime(), newHour);
                        entry.setTime(newHour);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else
                    Pointer.PrintAlert("modification failed ",
                            "the value you enter for the hour is not valid");
            }
        });
        return timesColumn;
    }

    private static TableColumn<WorkHourEntry, String> getWorkHourEntryStringTableColumn(String entName, DataSerialize d, Employee emp) {
        TableColumn<WorkHourEntry, String> dateColumn = new TableColumn<>("Day Of Work");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkHourEntry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkHourEntry, String> event) {
                WorkHourEntry entry = event.getRowValue();
                String newDate = event.getNewValue();
                if (UtilityWindowShowEnt.isValidDate(newDate)) {
                    try {
                        d.modifyDateWorkHour(entName, emp.getUuid(),
                                entry.getDate(), newDate, entry.getTime());
                        entry.setDate(newDate);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else
                    Pointer.PrintAlert("modification failed ",
                            "the value you enter for the date is not valid");

            }
        });
        return dateColumn;
    }
}
