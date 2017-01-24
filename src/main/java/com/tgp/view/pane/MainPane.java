package com.tgp.view.pane;

import com.tgp.db.dao.LogDao;
import com.tgp.image.ImageCapturer;
import com.tgp.model.Log;
import com.tgp.view.dialog.AdminLoginDialog;
import com.tgp.view.stage.AdminStage;
import com.tgp.view.util.Time;
import com.tgp.controller.UserController;
import com.tgp.db.Dbi;
import com.tgp.db.dao.UserDao;
import com.tgp.model.User;
import com.tgp.util.Pair;
import com.tgp.view.scene.AdminScene;
import com.tgp.view.stage.MainStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
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
         Log log = logDao.findInLogByTime(selectedUser.getId());
         if (log != null) {
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
         Log log = logDao.findOutLogByTime(selectedUser.getId());

         if (log != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
            String formattedDate = sdf.format(log.getTime());
            System.out.println(formattedDate);

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

      choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
         User selectedUser = userDao.findUserById(ids.get(newValue.intValue()));
         System.out.println(selectedUser.getFirstName());
      });

      gridPane.add(nameLabel, 0, 0);
      gridPane.add(choiceBox, 1, 0);
      gridPane.add(timeIn, 0, 2);
      gridPane.add(timeOut, 1, 2);

      setCenter(gridPane);

      HBox hbox = new HBox();
      hbox.setAlignment(Pos.BOTTOM_RIGHT);

      Button adminLogin = new Button("Admin Login");

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
      hbox.getChildren().add(adminLogin);

      setBottom(hbox);
   }

   private static final String PASSWORD = "root1234";
}
