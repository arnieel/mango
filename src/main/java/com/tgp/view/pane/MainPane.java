package com.tgp.view.pane;

import com.tgp.db.dao.LogDao;
import com.tgp.image.ImageCapturer;
import com.tgp.model.Log;
import com.tgp.util.FtlConfig;
import com.tgp.util.PdfBuilder;
import com.tgp.view.dialog.AdminLoginDialog;
import com.tgp.view.dialog.DatePickerDialog;
import com.tgp.view.stage.AdminStage;
import com.tgp.view.util.Time;
import com.tgp.controller.UserController;
import com.tgp.db.Dbi;
import com.tgp.db.dao.UserDao;
import com.tgp.model.User;
import com.tgp.util.Pair;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class MainPane extends BorderPane {

   private static UserController userController = new UserController();

   private static UserDao userDao = Dbi.getInstance().open(UserDao.class);

   private static LogDao logDao = Dbi.getInstance().open(LogDao.class);

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
         User selectedUser = userDao.findUserById(id);
         List<Log> log = logDao.findInLogForToday(selectedUser.getId());
         if (!log.isEmpty() && log.size() == 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Already logged in for today");
            alert.setContentText("You've already logged in!");
            alert.showAndWait();
         } else {
            String filename =  "C:/tgp/log/in/" + selectedUser.getFirstName()+selectedUser.getLastName()+System.currentTimeMillis()+".png";
            ImageCapturer.captureImage(filename);
            logDao.logUser(selectedUser.getId(), true, filename);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logged in");
            alert.setContentText("Logged in.");
            alert.showAndWait();
         }
      });

      Button timeOut = new Button("Time Out");
      timeOut.setOnAction(e -> {
         int id = ids.get(choiceBox.getSelectionModel().getSelectedIndex());
         User selectedUser = userDao.findUserById(id);
         List<Log> log = logDao.findOutLogForToday(selectedUser.getId());

         if (log != null && log.size() == 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Already logged out for today");
            alert.setContentText("You've already logged out!");
            alert.showAndWait();
         } else {
            String filename =  "C:/tgp/log/out/" + selectedUser.getFirstName()+selectedUser.getLastName()+System.currentTimeMillis()+".png";
            ImageCapturer.captureImage(filename);
            logDao.logUser(selectedUser.getId(), false, filename);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logged out");
            alert.setContentText("Logged out.");
            alert.showAndWait();
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
            if (!password.isEmpty() && password.equals(PASSWORD)) {
               getScene().getWindow().hide();
               new AdminStage();
            } else {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Warning Dialog");
               alert.setContentText("Password incorrect.");
               alert.showAndWait();
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

            for (User user : userDao.getAllUsers()) {
               List<Log> logs = logDao.getLogsByDateRangeAndByUser(startDate, endDate, user.getId());
               Map<String, Object> data = new HashMap<>();
               data.put("name", user.getLastName() + ", " + user.getFirstName());
               List<LogViewModel> logViewModelList = new ArrayList<>();
               for (Log log : logs) {
                  LogViewModel logView = new LogViewModel();
                  logView.setTime(log.getTime().toString());
                  logView.setLogType(log.isIn() ? "IN" : "OUT");
                  logView.setImagePath(log.getImagePath());
                  logViewModelList.add(logView);
               }

               data.put("logs", logViewModelList);

               String html = FtlConfig.get("template/report.html", data);
               htmlList.add(html);
            }

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(getScene().getWindow());
            if (file != null) {
               PdfBuilder.htmlsToPdf(htmlList, file);
            }
         } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not start program");
            StringWriter stackTraceWriter = new StringWriter();
            ex.printStackTrace(new PrintWriter(stackTraceWriter));
            TextArea textArea = new TextArea(stackTraceWriter.toString());
            alert.getDialogPane().setExpandableContent(textArea);
            alert.showAndWait();
         }
      }
   }

   public class LogViewModel {
      private String logType;
      private String time;
      private String imagePath;

      public void setImagePath(String imagePath) {
         this.imagePath = imagePath;
      }

      public void setLogType(String logType) {
         this.logType = logType;
      }

      public void setTime(String time) {
         this.time = time;
      }

      public String getImagePath() {
         return imagePath;
      }

      public String getLogType() {
         return logType;
      }

      public String getTime() {
         return time;
      }
   }

   private static final String PASSWORD = "root1234";
}
