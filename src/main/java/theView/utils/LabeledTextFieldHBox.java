package theView.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabeledTextFieldHBox extends HBox {
    private Label LTFLabel;
    private TextField LTFTextField;


    public LabeledTextFieldHBox(String label, String text, int widthTextField)
    {
        super();
        LTFLabel = new Label(label);
        LTFTextField = new TextField(text);
        LTFTextField.setDisable(true);
        LTFTextField.setMaxWidth(widthTextField);
        this.getChildren().addAll(LTFLabel, LTFTextField);
        this.setSpacing(10);

        this.setAlignment(Pos.CENTER);
    }


    public LabeledTextFieldHBox(String label, String text)
    {
        super();
        LTFLabel = new Label(label);
        LTFTextField = new TextField(text);
        LTFTextField.setDisable(true);
        LTFTextField.setMaxWidth(95);
        this.getChildren().addAll(LTFLabel, LTFTextField);
        this.setSpacing(10);

        this.setAlignment(Pos.CENTER);
    }


    public Label getLTFLabel() {
        return LTFLabel;
    }

    public void setLTFLabel(Label LTFLabel) {
        this.LTFLabel = LTFLabel;
    }

    public String getLTFTextFieldValue(){return LTFTextField.getText();}

    public void setLTFTextField(TextField LTFTextField) {
        this.LTFTextField = LTFTextField;
    }

    public void setDisableToTrue() {
        LTFTextField.setDisable(true);
    }

    public void setDisableToFalse() {
        LTFTextField.setDisable(false);
    }

    public void setPromptText(String text) {
        LTFTextField.setPromptText(text);
    }
}

