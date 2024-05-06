package theView.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LabeledIntel extends HBox {

    private Label title;
    private Label intel;

    public LabeledIntel(String theTitle, String theIntel)
    {
        title = new Label(theTitle);
        intel = new Label(theIntel);

        this.getChildren().addAll(title, intel);
        this.setSpacing(10);
    }
}
