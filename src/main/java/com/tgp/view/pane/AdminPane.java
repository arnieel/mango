package com.tgp.view.pane;


import com.tgp.view.stage.ManageUsersStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AdminPane extends BorderPane {

   public AdminPane() {
      Button manageUsersButton = new Button("Manage Users");

      manageUsersButton.setOnAction(e -> {
         getScene().getWindow().hide();
         new ManageUsersStage();
      });

      Button exportDataButton = new Button("Export Data");
      exportDataButton.setDisable(true);

      GridPane gridPane = new GridPane();
      gridPane.setAlignment(Pos.CENTER);
      gridPane.setHgap(10);
      gridPane.setVgap(12);

      gridPane.add(manageUsersButton, 0, 0);
      gridPane.add(exportDataButton, 0, 1);

      setCenter(gridPane);
   }
}
