package com.tgp.view.pane;

import com.tgp.controller.UserController;
import com.tgp.db.Dbi;
import com.tgp.db.dao.UserDao;
import com.tgp.model.User;
import com.tgp.util.Pair;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageUsersPane extends BorderPane {

   private static UserDao userDao = Dbi.getInstance().open(UserDao.class);

   private UserController userController = new UserController();

   public ManageUsersPane() {
      setPadding(new Insets(12, 12, 12, 40));

      Button addUserButton = new Button("Add User");
      Button editUserButton = new Button("Edit User");
      editUserButton.setDisable(true);
      Button deleteUserButton = new Button("Delete User");

      addUserButton.setOnAction(e -> {
         Dialog<Pair<String, String>> dialog = new Dialog<>();
         dialog.setTitle("Login Dialog");
         dialog.setHeaderText("Look, a Custom Login Dialog");

         ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
         dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

         GridPane grid = new GridPane();
         grid.setHgap(10);
         grid.setVgap(10);
         grid.setPadding(new Insets(20, 150, 10, 10));

         TextField firstname = new TextField();
         firstname.setPromptText("First Name");
         TextField lastname = new TextField();
         lastname.setPromptText("Last Name");

         grid.add(new Label("First Name:"), 0, 0);
         grid.add(firstname, 1, 0);
         grid.add(new Label("Last Name:"), 0, 1);
         grid.add(lastname, 1, 1);

         Node loginButton = dialog.getDialogPane().lookupButton(addButton);
         loginButton.setDisable(true);

         firstname.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
         });

         dialog.getDialogPane().setContent(grid);

         dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
               return new Pair<>(firstname.getText(), lastname.getText());
            }
            return null;
         });

         Optional<Pair<String, String>> result = dialog.showAndWait();

         result.ifPresent(name -> {
            userDao.insertUser(name._1(), name._2());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User added");
            alert.setContentText(name._1() + " " + name._2() + " added.");
            alert.showAndWait();
         });
      });

      editUserButton.setOnAction(e -> {

      });

      deleteUserButton.setOnAction(e -> {
         List<Integer> ids = new ArrayList<>();
         List<String> names = new ArrayList<>();

         for (Pair<Integer, String> pair : userController.getListOfNames()) {
            ids.add(pair._1());
            names.add(pair._2());
         }

         ChoiceDialog deleteUserDialog = new ChoiceDialog("", names);
         deleteUserDialog.setTitle("Delete User");
         deleteUserDialog.setHeaderText("Delete a user");
         deleteUserDialog.setContentText("");

         Optional<String> result = deleteUserDialog.showAndWait();
         if (result.isPresent()) {
            if (!result.get().isEmpty()) {
               String[] lastNameFirstName = result.get().split(",");
               String firstName = lastNameFirstName[1].trim();
               String lastName = lastNameFirstName[0].trim();
               User user = userDao.findUserByName(firstName, lastName);
               userDao.deleteUser(user.getId());
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("User deleted");
               alert.setContentText("User Deleted");
               alert.showAndWait();
            }
         }
      });

      VBox vbox = new VBox();
      vbox.setSpacing(20);

      vbox.setAlignment(Pos.CENTER_LEFT);
      vbox.getChildren().add(addUserButton);
      vbox.getChildren().add(editUserButton);
      vbox.getChildren().add(deleteUserButton);

      setCenter(vbox);
   }
}
