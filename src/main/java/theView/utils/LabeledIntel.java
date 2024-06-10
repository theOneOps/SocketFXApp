package theView.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * LabeledIntel is a custom JavaFX component that combines two labels within an HBox.
 * This component is useful for displaying a piece of information with a title.
 */
public class LabeledIntel extends HBox {

    private Label title;  // The label for the title
    private Label intel;  // The label for the information

    /**
     * Constructs a LabeledIntel with a specified title and information.
     *
     * @param theTitle the title to display
     * @param theIntel the information to display
     */
    public LabeledIntel(String theTitle, String theIntel) {
        title = new Label(theTitle);
        intel = new Label(theIntel);

        this.getChildren().addAll(title, intel); // Add the title and information labels to the HBox
        this.setSpacing(10); // Set the spacing between the labels
    }
}
