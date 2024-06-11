package theView.utils;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * LabeledComboHBox is a custom JavaFX component that combines a label and a combo box within an HBox.<br>
 * This component is useful for displaying a label next to a combo box for selecting items.<br>
 * Its attributes are:
 * <ul>
 *     <li>{@code LCBLabel} Label : the label component</li>
 *     <li>{@code LCBComboBox} ComboBox : the combo box component</li>
 *     </ul>
 */
public class LabeledComboHBox extends HBox {

    private Label LCBLabel;        // The label component
    private ComboBox<String> LCBComboBox; // The combo box component

    /**
     * Constructs a LabeledComboHBox with a specified label and an array of items.
     *
     * @param label the label text
     * @param items the items to add to the combo box
     */
    public LabeledComboHBox(String label, String[] items) {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);

        this.getChildren().addAll(LCBLabel, LCBComboBox); // Add the label and combo box to the HBox
        this.setAlignment(Pos.CENTER); // Center align the elements in the HBox
        this.setSpacing(10); // Set the spacing between elements in the HBox

        if (items.length != 0) {
            LCBComboBox.setValue(items[0]); // Set the first item as the default selected value
        }
    }

    /**
     * Constructs a LabeledComboHBox with a specified label, an array of items, and a custom spacing between components.
     *
     * @param label                  the label text
     * @param items                  the items to add to the combo box
     * @param spaceBetweenComponents the spacing between the label and the combo box
     */
    LabeledComboHBox(String label, String[] items, int spaceBetweenComponents) {
        super();
        LCBLabel = new Label(label);
        LCBComboBox = new ComboBox<>();
        LCBComboBox.getItems().addAll(items);
        LCBComboBox.setValue(items[0]); // Set the first item as the default selected value

        this.getChildren().addAll(LCBLabel, LCBComboBox); // Add the label and combo box to the HBox
        this.setAlignment(Pos.CENTER); // Center align the elements in the HBox
        this.setSpacing(spaceBetweenComponents); // Set the custom spacing between elements in the HBox
    }

    /**
     * Gets the label component.
     *
     * @return the label component
     */
    public Label getLCBLabel() {
        return LCBLabel;
    }

    /**
     * Sets the label component.
     *
     * @param LCBLabel the label to set
     */
    public void setLCBLabel(Label LCBLabel) {
        this.LCBLabel = LCBLabel;
    }

    /**
     * Gets the combo box component.
     *
     * @return the combo box component
     */
    public ComboBox<String> getLCBComboBox() {
        return LCBComboBox;
    }

    /**
     * Sets the items of the combo box and selects the first item by default.
     *
     * @param array the array of items to add to the combo box
     */
    public void setLCBComboBox(String[] array) {
        this.LCBComboBox.getItems().addAll(array);
        LCBComboBox.setValue(array[0]); // Set the first item as the default selected value
    }

    /**
     * Clears all items from the combo box.
     */
    public void clearLCBComboBox() {
        this.LCBComboBox.getItems().clear();
    }
}
