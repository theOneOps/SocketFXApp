package theView.utils;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LabeledComboHBox extends HBox {

    private Label LCBLabel;
    private ComboBox<String> LCBComboBox;


    public LabeledComboHBox(String label, String[] items)
    {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);


        this.getChildren().addAll(LCBLabel, LCBComboBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        if (items.length != 0)
            LCBComboBox.setValue(items[0]);
    }

    LabeledComboHBox(String label, String[] items, int spaceBetweenComponents)
    {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);
        LCBComboBox.setValue(items[0]);


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

    public void setLCBComboBox(String[] array) {
        this.LCBComboBox.getItems().addAll(array);
        LCBComboBox.setValue(array[0]);
    }

    public void clearLCBComboBox() {
        this.LCBComboBox.getItems().clear();
    }
}
