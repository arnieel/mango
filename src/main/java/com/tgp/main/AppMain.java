package com.tgp.main;

import com.tgp.view.scene.MainScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AppMain extends Application {

   @Override
   public void start(final Stage primaryStage) throws Exception {
      primaryStage.setTitle(TITLE);

      Scene mainScene = new MainScene();
      primaryStage.setTitle(TITLE);
      primaryStage.setScene(mainScene);
      primaryStage.show();
      primaryStage.setResizable(false);
      primaryStage.setOnCloseRequest(e -> {
         Platform.exit();
         System.exit(0);
      });
   }

   public static void main(String[] args) {
      try {
         launch(args);
      } catch (Exception e) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         StringWriter stackTraceWriter = new StringWriter();
         e.printStackTrace(new PrintWriter(stackTraceWriter));
         alert.setContentText(e.toString() + "\n" + stackTraceWriter.toString());
         alert.showAndWait();
      }
   }

   private static final String TITLE = "Time Tracker";


}
