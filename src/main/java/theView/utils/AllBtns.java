package theView.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AllBtns extends HBox {
    private Button btn1;
    private Button btn2;


    public AllBtns(String btn1Text, String btn2Text) {
        super();
        btn1 = new Button(btn1Text);
        btn2 = new Button(btn2Text);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setSpacing(10);

        this.getChildren().addAll(btn1,spacer, btn2);

    }

    public void setBtn1Action(EventHandler<ActionEvent> e)
    {
        btn1.setOnAction(e);
    }

    public void setBtn2Action(EventHandler<ActionEvent> e)
    {
        btn2.setOnAction(e);
    }
}
