package com.tgp.view.dialog;

import com.tgp.util.Pair;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.format.DateTimeFormatter;

public class DatePickerDialog extends Dialog<Pair<String, String>> {

    public DatePickerDialog() {
        setTitle("Select Date");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker startDate = new DatePicker();
        DatePicker endDate = new DatePicker();

        grid.add(new Label("Start Date:"), 0, 0);
        grid.add(startDate, 1, 0);
        grid.add(new Label("End Date:"), 0, 1);
        grid.add(endDate, 1, 1);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(startDate.getValue().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                        endDate.getValue().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            }
            return null;
        });
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd";
}
