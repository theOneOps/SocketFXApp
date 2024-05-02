package theView.utils;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabeledComboVBox extends VBox {

    private Label LCBLabel;
    private ComboBox<String> LCBComboBox;


    public LabeledComboVBox(String label, String[] items)
    {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);
        LCBComboBox.setValue(items[0]);


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
}
