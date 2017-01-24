package com.tgp.view.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Created by Arniel on 1/6/2017.
 */
public class AdminLoginDialog extends Dialog<String> {

   public AdminLoginDialog() {
      setTitle("Admin Login");

      // Set the button types.
      ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
      getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

      // Create the username and password labels and fields.
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 150, 10, 10));

      PasswordField password = new PasswordField();
      password.setPromptText("Password");

      grid.add(new Label("Password:"), 0, 0);
      grid.add(password, 1, 0);

      getDialogPane().setContent(grid);

      setResultConverter(dialogButton -> {
         if (dialogButton == loginButtonType) {
            return password.getText();
         }
         return null;
      });
   }
}
