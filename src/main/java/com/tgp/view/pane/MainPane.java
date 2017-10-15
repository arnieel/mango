package com.tgp.view.pane;

import com.tgp.controller.LogController;
import com.tgp.controller.UserController;
import com.tgp.model.Log;
import com.tgp.model.User;
import com.tgp.util.FtlConfig;
import com.tgp.util.Pair;
import com.tgp.util.PasswordUtil;
import com.tgp.util.PdfBuilder;
import com.tgp.view.alert.AlertFactory;
import com.tgp.view.dialog.AdminLoginDialog;
import com.tgp.view.dialog.DatePickerDialog;
import com.tgp.view.stage.AdminStage;
import com.tgp.util.Time;
import com.tgp.view.viewmodel.LogViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPane extends BorderPane {

    private static UserController userController = new UserController();

    private static LogController logController = new LogController();

    public MainPane() {
        setPadding(new Insets(12, 12, 12, 12));

        Label timeLabel = new Label();
        Time time = new Time();
        time.activateTimer(timeLabel);

        setTop(timeLabel);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(12);

        Label nameLabel = new Label("Name:");
        ChoiceBox choiceBox = new ChoiceBox();
        List<Integer> ids = new ArrayList<>();

        for (Pair<Integer, String> pair : userController.getListOfNames()) {
            ids.add(pair._1());
            choiceBox.getItems().add(pair._2());
        }

        Button timeIn = new Button("Time In");
        timeIn.setOnAction(e -> {
            int id = ids.get(choiceBox.getSelectionModel().getSelectedIndex());
            User selectedUser = userController.findUserById(id);
            List<Log> log = logController.findInLogForToday(selectedUser.getId());
            if (!log.isEmpty() && log.size() == 2) {
                AlertFactory.showWarningAlert("Already logged in for today", "You've already logged in!");
            } else {
                logController.logUser(selectedUser.getId(), true, "");
                AlertFactory.showInformationAlert("Logged in", "Logged in.");
            }
        });

        Button timeOut = new Button("Time Out");
        timeOut.setOnAction(e -> {
            int id = ids.get(choiceBox.getSelectionModel().getSelectedIndex());
            User selectedUser = userController.findUserById(id);
            List<Log> log = logController.findOutLogForToday(selectedUser.getId());

            if (log != null && log.size() == 2) {
                AlertFactory.showWarningAlert("Already logged out for today", "You've already logged out!");
            } else {
                logController.logUser(selectedUser.getId(), false, "");
                AlertFactory.showInformationAlert("Logged out", "Logged out.");
            }
        });

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(choiceBox, 1, 0);
        gridPane.add(timeIn, 0, 2);
        gridPane.add(timeOut, 1, 2);

        setCenter(gridPane);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        Button adminLogin = new Button("Admin Login");

        Button exportDataButton = new Button("Export Data");
        exportDataButton.setOnAction(e -> exportDataAction());

        AdminLoginDialog adminLoginDialog = new AdminLoginDialog();
        adminLogin.setOnAction(e -> {
            Optional<String> result = adminLoginDialog.showAndWait();

            result.ifPresent(password -> {
                if (PasswordUtil.checkPassword(password)) {
                    getScene().getWindow().hide();
                    new AdminStage();
                } else {
                    AlertFactory.showIncorrectPasswordAlert();
                }
            });
        });
        hbox.getChildren().add(exportDataButton);
        hbox.getChildren().add(adminLogin);

        setBottom(hbox);
    }

    private void exportDataAction() {
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        Optional<Pair<String, String>> result = datePickerDialog.showAndWait();
        if (result.isPresent()) {
            try {
                String startDate = result.get()._1();
                String endDate = result.get()._2();

                List<String> htmlList = new ArrayList<>();

                for (User user : userController.getAllUsers()) {
                    List<Log> logs = logController.getLogsByDateRangeAndByUser(startDate, endDate, user.getId());
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", user.getLastName() + ", " + user.getFirstName());
                    List<LogViewModel> logViewModelList = new ArrayList<>();
                    for (Log log : logs) {
                        LogViewModel logView = new LogViewModel();
                        logView.setTime(formatTimeStamp(log.getTime()));
                        logView.setLogType(log.isIn() ? "IN" : "OUT");
                        logView.setImagePath(log.getImagePath());
                        logViewModelList.add(logView);
                    }

                    data.put("logs", logViewModelList);

                    String html = FtlConfig.get("template/report.html", data);
                    htmlList.add(html);
                }

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialFileName(INITIAL_FILENAME);
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

                File file = fileChooser.showSaveDialog(getScene().getWindow());
                if (file != null) {
                    PdfBuilder.exportHtmlToPdf(htmlList, file);
                }
            } catch (Exception e) {
                AlertFactory.showErrorAlert(e);
            }
        }
    }

    private String formatTimeStamp(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(timestamp);
    }

    private static final String INITIAL_FILENAME = "export.pdf";

    private static final String DATE_FORMAT = "MMM-dd-yyyy hh:mm:ss a";
}
