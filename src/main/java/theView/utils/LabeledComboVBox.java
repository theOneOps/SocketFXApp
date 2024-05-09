package theView.utils;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabeledComboVBox extends VBox {

    private Label LCBLabel;
    private ComboBox<String> LCBComboBox;
    private String currentValue;


    public LabeledComboVBox(String label, String[] items)
    {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);

        if (items.length != 0)
            LCBComboBox.setValue(items[0]);

        LCBComboBox.setOnAction(e->{
            currentValue = LCBComboBox.getSelectionModel().getSelectedItem();
        });

        this.getChildren().addAll(LCBLabel, LCBComboBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    LabeledComboVBox(String label, String[] items,  int spaceBetweenComponents)
    {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);
        LCBComboBox.setValue(items[0]);

        LCBComboBox.setOnAction(e->{
            currentValue = LCBComboBox.getSelectionModel().getSelectedItem();
        });

        this.getChildren().addAll(LCBLabel, LCBComboBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(spaceBetweenComponents);
    }

    public Label getLCBLabel() {
        return LCBLabel;
    }

    public void setLCBLabel(Label LCBLabel) {
        this.LCBLabel = LCBLabel;
    }

    public ComboBox<String> getLCBComboBox() {
        return LCBComboBox;
    }

    public void setLCBComboBox(ComboBox<String> LCBComboBox) {
        this.LCBComboBox = LCBComboBox;
    }

    public void setLCBComboBox(String[] array) {
        this.LCBComboBox.getItems().addAll(array);
        LCBComboBox.setValue(array[0]);
    }

    public void clearLCBComboBox() {
        this.LCBComboBox.getItems().clear();
    }

    public String getSelectedValue()
    {
        return currentValue;
    }
}
