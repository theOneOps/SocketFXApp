package theView.manage.windowShowEnt;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import theModel.JobClasses.Enterprise;
import theModel.JobClasses.WorkHour;

public class WorkHourTableView {

    private static ObservableList<WorkHour> dataPointers;
    private static TableView<WorkHour> tablePointer;

    public static VBox seeTableAllPointers(Enterprise ent, boolean seeAllPointers) {
        VBox EmpView = new VBox();

        tablePointer = new TableView<>();
        tablePointer.setEditable(true);

        TableColumn<WorkHour, String> dayOfWork = new TableColumn<>("Day Of work");
        TableColumn<WorkHour, String> uuidColumn = new TableColumn<>("UUID");
        TableColumn<WorkHour, String> empStartHour = new TableColumn<>("Starting Hour");
        TableColumn<WorkHour, String> empEndHour = new TableColumn<>("Ending Hour");

        dayOfWork.setCellValueFactory(new PropertyValueFactory<>("dateWork"));
        uuidColumn.setCellValueFactory(new PropertyValueFactory<>("empID"));
        empStartHour.setCellValueFactory(new PropertyValueFactory<>("hourStart"));
        empEndHour.setCellValueFactory(new PropertyValueFactory<>("hourEnd"));

        tablePointer.getColumns().addAll(dayOfWork, uuidColumn, empStartHour, empEndHour);
        tablePointer.setItems(dataPointers);

        EmpView.getChildren().addAll(tablePointer);
        EmpView.setSpacing(5);

        return EmpView;
    }

//    private static void loadDataAllPointers(Enterprise ent) {
//        ArrayList<WorkHour> array = new ArrayList<>();
//        Collection<ArrayList<WorkHour>> AllWorkHour = ent.getWorkHours().values();
//        for (ArrayList<WorkHour> CollectionWk : AllWorkHour) {
//            for (WorkHour wk : CollectionWk) {
//                array.add(new WorkHour(wk.getEmpID(), wk.getHourStart(), wk.getHourEnd(), wk.getDateWork()));
//            }
//        }
//        dataPointers = FXCollections.observableArrayList(array);
//    }
//
//    private static void loadDataDailyPointers(Enterprise ent) {
//        ArrayList<WorkHour> array = new ArrayList<>();
//        Collection<ArrayList<WorkHour>> AllWorkHour = ent.getWorkHours().values();
//        for (ArrayList<WorkHour> CollectionWk : AllWorkHour) {
//            for (WorkHour wk : CollectionWk) {
//                if (wk.getDateWork().equals(LocalDate.now().toString()))
//                    array.add(new WorkHour(wk.getEmpID(), wk.getHourStart(), wk.getHourEnd(), wk.getDateWork()));
//            }
//        }
//        dataPointers = FXCollections.observableArrayList(array);
//    }
}
