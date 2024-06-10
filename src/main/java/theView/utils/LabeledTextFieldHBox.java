package theView.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * LabeledTextFieldHBox is a custom JavaFX component that combines a label and a text field within an HBox.
 * The text field can be enabled or disabled, and its size can be set.
 */
public class LabeledTextFieldHBox extends HBox {
    private Label LTFLabel;      // The label component
    private TextField LTFTextField; // The text field component

    /**
     * Constructs a LabeledTextFieldHBox with a specified label, text, and text field width.
     *
     * @param label         the label text
     * @param text          the initial text for the text field
     * @param widthTextField the maximum width of the text field
     */
    public LabeledTextFieldHBox(String label, String text, int widthTextField) {
        super();
        LTFLabel = new Label(label);
        LTFTextField = new TextField(text);
        LTFTextField.setDisable(true); // Initially disable the text field
        LTFTextField.setMaxWidth(widthTextField); // Set the maximum width of the text field
        this.getChildren().addAll(LTFLabel, LTFTextField); // Add the label and text field to the HBox
        this.setSpacing(10); // Set the spacing between elements in the HBox
        this.setAlignment(Pos.CENTER); // Center align the elements in the HBox
    }

    /**
     * Constructs a LabeledTextFieldHBox with a specified label and text.
     * The default width of the text field is set to 95.
     *
     * @param label the label text
     * @param text  the initial text for the text field
     */
    public LabeledTextFieldHBox(String label, String text) {
        super();
        LTFLabel = new Label(label);
        LTFTextField = new TextField(text);
        LTFTextField.setDisable(true); // Initially disable the text field
        LTFTextField.setMaxWidth(95); // Set the default maximum width of the text field
        this.getChildren().addAll(LTFLabel, LTFTextField); // Add the label and text field to the HBox
        this.setSpacing(10); // Set the spacing between elements in the HBox
        this.setAlignment(Pos.CENTER); // Center align the elements in the HBox
    }

    /**
     * Gets the label component.
     *
     * @return the label component
     */
    public Label getLTFLabel() {
        return LTFLabel;
    }

    /**
     * Sets the label component.
     *
     * @param LTFLabel the label to set
     */
    public void setLTFLabel(Label LTFLabel) {
        this.LTFLabel = LTFLabel;
    }

    /**
     * Gets the value of the text field.
     *
     * @return the value of the text field
     */
    public String getLTFTextFieldValue() {
        return LTFTextField.getText();
    }

    /**
     * Sets the value of the text field.
     *
     * @param txt the value to set in the text field
     */
    public void setLTFTextFieldValue(String txt) {
        LTFTextField.setText(txt);
    }

    /**
     * Sets the text field component.
     *
     * @param LTFTextField the text field to set
     */
    public void setLTFTextField(TextField LTFTextField) {
        this.LTFTextField = LTFTextField;
    }

    /**
     * Disables the text field.
     */
    public void setDisableToTrue() {
        LTFTextField.setDisable(true);
    }

    /**
     * Enables the text field.
     */
    public void setDisableToFalse() {
        LTFTextField.setDisable(false);
    }

    /**
     * Sets the prompt text for the text field.
     *
     * @param text the prompt text to set
     */
    public void setPromptText(String text) {
        LTFTextField.setPromptText(text);
    }
}
