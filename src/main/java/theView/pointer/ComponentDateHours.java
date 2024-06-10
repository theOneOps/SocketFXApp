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

/**
 * This class represents a custom JavaFX component that displays the current date, time, and a rounded time.
 * <p>
 * The component updates the time every second.
 */
public class ComponentDateHours extends HBox {
    private Label date;      // Label to display the current date
    private Label hours;     // Label to display the current hour
    private Label roundHours; // Label to display the rounded hour
    private LocalTime time;  // The current local time

    /**
     * Constructor for ComponentDateHours.
     * <p>
     * Initializes the labels and starts a timeline to update the time every second.
     */
    public ComponentDateHours() {
        super();
        date = new Label(String.format("Date : %s", LocalDate.now())); // Initialize the date label with the current date

        time = LocalTime.now();
        hours = new Label("");  // Initialize the hours label
        roundHours = new Label("");  // Initialize the roundHours label

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

        this.getChildren().addAll(date, spacer, hours, roundHours); // Add the labels to the HBox
    }

    /**
     * Rounds the current time to the nearest 15-minute interval.
     *
     * @return A string representing the rounded time in HH:mm format.
     */
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
