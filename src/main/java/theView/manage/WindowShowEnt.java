package theView.manage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import theModel.DataSerialize;
import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;
import theView.pointer.Pointer;
import theView.utils.LabeledIntel;
import theView.utils.LabeledTextFieldHBox;

import java.io.IOException;
import java.util.ArrayList;

public class WindowShowEnt {

    private static ObservableList<Employee> data;
    private static Pane content;

    public static void showEnterpriseContent(DataSerialize d, Enterprise ent) {

        // Configurer la scène et la table
        Stage stage = new Stage();
        stage.setTitle(String.format("Enterprise '%s' management", ent.getEntname()));

        MenuBar theMenu = new MenuBar();
        final Menu SeeEmp = new Menu("See all Employees");
        final Menu AllPointers =new Menu("All Pointers");

        final RadioMenuItem savePointers = new RadioMenuItem("All save pointers");
        final RadioMenuItem currentPointers = new RadioMenuItem("All current pointers");
        AllPointers.getItems().addAll(savePointers, currentPointers);
        theMenu.getMenus().addAll(SeeEmp, AllPointers);

        content = new Pane();

        SeeEmp.setOnAction(e->{
            content.getChildren().clear();
            content.getChildren().add(seeTableAllEmp(d, ent));
        });

        VBox container = new VBox();

        Pane detailsOnEmp = new Pane();

        HBox contents = new HBox();

        contents.getChildren().addAll(seeTableAllEmp(d, ent), detailsOnEmp);
        container.getChildren().addAll(theMenu, contents);
        container.setSpacing(5);

        Scene scene = new Scene(container, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    private static void loadData(Enterprise ent)
    {
        ArrayList<Employee> array = new ArrayList<>();
        for (Employee emp : ent.getEmployees().values()) {

            array.add(new Employee(emp.getUuid(), emp.getEmpName(), emp.getEmpPrename(),
                    emp.getStartingHour(),
                    emp.getEndingHour()
            ));
        }
        data = FXCollections.observableArrayList(array);
    }

    private static VBox seeTableAllEmp(DataSerialize d, Enterprise ent) {

        VBox EmpView = new VBox();

        // Créer une liste observable des employés

        loadData(ent);

        TableView<Employee> table = new TableView<>();
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
        table.setItems(data);

        Pane detailsEmp = new Pane();

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Vérification que le clic est un double clic
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                //System.out.println("Double clic sur : " + selectedItem);
                // Faire ce que tu veux avec l'élément sélectionné
                detailsEmp.getChildren().clear();
                detailsEmp.getChildren().addAll(getDetailsOnEmp(selectedItem));
            }
        });

        // Ajout de l'event handler pour le clic droit sur une ligne
        // Remove an employee from the enterprise by a right click on the employee's record
        table.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("SECONDARY")) {
                Employee selectedItem = table.getSelectionModel().getSelectedItem();
                System.out.println("Clic droit sur : " + selectedItem);
                try {
                    d.removeEmployeeFromEnterprise(ent.getEntname(), selectedItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                data.remove(selectedItem);
            }
        });

        HBox tableAndDetails = new HBox();
        tableAndDetails.setSpacing(10);
        tableAndDetails.getChildren().addAll(table, detailsEmp);

        EmpView.getChildren().addAll(tableAndDetails, addEmp(ent, d));
        EmpView.setSpacing(5);
        return EmpView;

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
                    data.add(newEmp);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                name.setLTFTextFieldValue("");
                prename.setLTFTextFieldValue("");
                startHour.setLTFTextFieldValue("");
                endHour.setLTFTextFieldValue("");

                loadData(ent);

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
        //LabeledIntel department = new LabeledIntel("Department", emp.getEndingHour());

        detailsIntel.getChildren().addAll(titleDetails, startHour, endHour);

        return detailsIntel;
    }

}
