package theView.pointer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ComponentDateHours extends HBox {
    private Label date;
    private Label hours;
    private Label roundHours;
    private LocalTime time;



    ComponentDateHours() {
        super();
        date = new Label(String.format("Date : %s", LocalDate.now()));

        time = LocalTime.now();
        hours = new Label("");  // Initialisation du champ 'hours'
        roundHours = new Label("");  // Initialisation du champ 'roundHours'


        Timeline tm = new Timeline();
        tm.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> {
            hours.setText(String.format("Current Hour : %s", DateTimeFormatter.ofPattern("HH:mm")
                    .format(LocalTime.now())));
            roundHours.setText(String.format("Round Hour : %s", roundTime()));
        }));
        tm.getKeyFrames().add(new KeyFrame(Duration.seconds(1))); // update every second
        tm.setCycleCount(Timeline.INDEFINITE); // repeat indefinitely
        tm.play();

        this.setSpacing(5);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren
                ().addAll(date,spacer, hours, roundHours);

    }


    public String roundTime() {
        int minutes = time.getMinute();
        int modMinutes = minutes % 15;
        int minutesToAdd = (modMinutes <= 7) ? -modMinutes : (15 - modMinutes);

        LocalTime roundedTime = time.plusMinutes(minutesToAdd);
        String res;
        if (roundedTime.getHour() <= 9)
            res = String.format("0%s", roundedTime.getHour());
        else
            res = String.valueOf(roundedTime.getHour());
        return String.format("%s:%02d", res, roundedTime.getMinute());
    }
}

