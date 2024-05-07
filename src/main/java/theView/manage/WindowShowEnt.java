package theView.manage;

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
import theView.pointer.Pointer;
import theView.utils.LabeledIntel;
import theView.utils.LabeledTextFieldHBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class WindowShowEnt {

    private static ObservableList<Employee> dataEmps;
    private static ObservableList<WorkHour> dataPointers;
    private static HBox contents;
    private static TableView<Employee> table;
    private static TableView<WorkHour> tablePointer;

    public static void showEnterpriseContent(DataSerialize d, Enterprise ent) {

        // Configurer la scène et la table
        Stage stage = new Stage();
        stage.setTitle(String.format("Enterprise '%s' management", ent.getEntname()));

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
        container.getChildren().addAll(theMenu, contents);
        container.setSpacing(5);

        Scene scene = new Scene(container, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    private static VBox seeTableAllPointers(Enterprise ent, boolean seeAllPointers)
    {
        VBox EmpView = new VBox();

        // Créer une liste observable des employés
        if (seeAllPointers)
            loadDataAllPointers(ent);
        else
            loadDataDailyPointers(ent);

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
        TableColumn<Employee, String> nameColumn = new TableColumn<>("first name");
        TableColumn<Employee, String> prenameColumn = new TableColumn<>("Prename");

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
        // Ajouter les colonnes au `TableView`
        table.getColumns().addAll(uuidcolumn,nameColumn, prenameColumn); // workingHour

        table.setMinWidth(380);
        // Ajouter les données dans la table
        table.setItems(dataEmps);

        Pane detailsEmp = new Pane();

        table.setOnMouseClicked(event -> {
            // to print details about an employee
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                //System.out.println("Double clic sur : " + selectedItem);
                detailsEmp.getChildren().clear();
                detailsEmp.getChildren().add(getDetailsOnEmp(selectedItem));
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

        EmpView.getChildren().addAll(tableAndDetails, addEmp(ent, d));
        EmpView.setSpacing(5);
        return EmpView;

    }

    private static void loadDataAllPointers(Enterprise ent)
    {
        ArrayList<WorkHour> array = new ArrayList<>();
        Collection<ArrayList<WorkHour>> AllWorkHour = ent.getWorkHours().values();
        for(ArrayList<WorkHour> CollectionWk : AllWorkHour)
        {
            for(WorkHour wk : CollectionWk)
            {
                array.add(new WorkHour(wk.getEmpID(), wk.getHourStart(), wk.getHourEnd(), wk.getDateWork()));
            }
        }

        dataPointers = FXCollections.observableArrayList(array);
    }

    private static void loadDataDailyPointers(Enterprise ent)
    {
        ArrayList<WorkHour> array = new ArrayList<>();
        Collection<ArrayList<WorkHour>> AllWorkHour = ent.getWorkHours().values();
        for(ArrayList<WorkHour> CollectionWk : AllWorkHour)
        {
            for(WorkHour wk : CollectionWk)
            {
                if (wk.getDateWork().equals(LocalDate.now().toString()))
                    array.add(new WorkHour(wk.getEmpID(), wk.getHourStart(), wk.getHourEnd(), wk.getDateWork()));
            }
        }

        dataPointers = FXCollections.observableArrayList(array);
    }

    private static void loadDataEmp(Enterprise ent)
    {
        ArrayList<Employee> array = new ArrayList<>();
        for (Employee emp : ent.getEmployees().values()) {

            array.add(new Employee(emp.getUuid(), emp.getEmpName(), emp.getEmpPrename(),
                    emp.getStartingHour(),
                    emp.getEndingHour()
            ));
        }
        dataEmps = FXCollections.observableArrayList(array);
    }

    private static HBox addEmp(Enterprise ent, DataSerialize d)
    {
        HBox contentAddEmp = new HBox();

        LabeledTextFieldHBox name = new LabeledTextFieldHBox("name", "");

        LabeledTextFieldHBox prename = new LabeledTextFieldHBox("prename", "");

        LabeledTextFieldHBox startHour = new LabeledTextFieldHBox("starting hour", "");

        LabeledTextFieldHBox endHour = new LabeledTextFieldHBox("ending hour", "");

        Button send = new Button("create");

        send.setOnAction(e->{
            if (!name.getLTFTextFieldValue().isEmpty() &&
                    !prename.getLTFTextFieldValue().isEmpty()&&
                    !startHour.getLTFTextFieldValue().isEmpty() &&
                    !endHour.getLTFTextFieldValue().isEmpty())
            {
                try {
                    Employee newEmp = new Employee(name.getLTFTextFieldValue(),
                            prename.getLTFTextFieldValue(),startHour.getLTFTextFieldValue(),
                            endHour.getLTFTextFieldValue());

                    d.addNewEmployeeToEnterprise(ent.getEntname(), newEmp);

                    // updating the data of the tableView
                    dataEmps.add(newEmp);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                name.setLTFTextFieldValue("");
                prename.setLTFTextFieldValue("");
                startHour.setLTFTextFieldValue("");
                endHour.setLTFTextFieldValue("");

                Pointer.PrintAlert("creation of new employee","employee added successfully !");
            }
        });

        name.setDisableToFalse();
        prename.setDisableToFalse();
        startHour.setDisableToFalse();
        endHour.setDisableToFalse();

        contentAddEmp.getChildren().addAll(name, prename, startHour, endHour, send);

        contentAddEmp.setSpacing(2);

        return contentAddEmp;
    }

    private static VBox getDetailsOnEmp(Employee emp)
    {
        VBox detailsIntel = new VBox();

        Label titleDetails = new Label(String.format("details on the employee '%s' ",emp.getEmpName()));

        LabeledIntel startHour = new LabeledIntel("Starting Hour", emp.getStartingHour());
        LabeledIntel endHour = new LabeledIntel("Ending Hour", emp.getEndingHour());
        //LabeledIntel department = new LabeledIntel("Department", emp.getDepartment());

        detailsIntel.getChildren().addAll(titleDetails, startHour, endHour);

        return detailsIntel;
    }

}
