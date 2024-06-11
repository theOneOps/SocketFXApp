package theView.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * AllBtns is a custom JavaFX component that creates a horizontal box containing up to three buttons.<br>
 * It provides constructors for creating two or three buttons with optional action handlers.<br>
 * Its attributes are:
 * <ul>
 *     <li>{@code btn1} Button : the first button</li>
 *     <li>{@code btn2} Button : the second button</li>
 *     <li>{@code btn3} Button : the third button, (optional)</li>
 *     </ul>
 */
public class AllBtns extends HBox {
    private Button btn1; // The first button
    private Button btn2; // The second button
    private Button btn3; // The third button, optional

    /**
     * Constructs an AllBtns instance with two buttons.
     *
     * @param btn1Text the text for the first button
     * @param btn2Text the text for the second button
     */
    public AllBtns(String btn1Text, String btn2Text) {
        super();
        btn1 = new Button(btn1Text);
        btn2 = new Button(btn2Text);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setSpacing(10);

        this.getChildren().addAll(btn1, spacer, btn2);
    }

    /**
     * Constructs an AllBtns instance with three buttons.
     *
     * @param btn1Text the text for the first button
     * @param btn2Text the text for the second button
     * @param btn3Text the text for the third button
     */
    public AllBtns(String btn1Text, String btn2Text, String btn3Text) {
        super();
        btn1 = new Button(btn1Text);
        btn2 = new Button(btn2Text);
        btn3 = new Button(btn3Text);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setSpacing(10);

        this.getChildren().addAll(btn1, spacer, btn2, btn3);
    }

    /**
     * Sets the action handler for the first button.
     *
     * @param e the event handler to set for the first button
     */
    public void setBtn1Action(EventHandler<ActionEvent> e) {
        btn1.setOnAction(e);
    }

    /**
     * Sets the action handler for the second button.
     *
     * @param e the event handler to set for the second button
     */
    public void setBtn2Action(EventHandler<ActionEvent> e) {
        btn2.setOnAction(e);
    }

    /**
     * Gets the first button.
     *
     * @return the first button
     */
    public Button getBtn1() {
        return btn1;
    }

    /**
     * Gets the second button.
     *
     * @return the second button
     */
    public Button getBtn2() {
        return btn2;
    }

    /**
     * Gets the third button, if it exists.
     *
     * @return the third button, or null if it does not exist
     */
    public Button getBtn3() {
        return btn3;
    }
}
