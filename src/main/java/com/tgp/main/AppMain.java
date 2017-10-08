package com.tgp.main;

import com.tgp.view.alert.AlertFactory;
import com.tgp.view.scene.MainScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AppMain extends Application {

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        try {
            Scene mainScene = new MainScene();
            primaryStage.setTitle(TITLE);
            primaryStage.setScene(mainScene);
            primaryStage.show();
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (Exception e) {
            Platform.runLater(() -> {
               AlertFactory.showErrorAlert(e);
            });

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static final String TITLE = "Time Tracker";


}
