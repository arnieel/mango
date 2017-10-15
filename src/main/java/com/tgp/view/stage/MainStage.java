package com.tgp.view.stage;

import com.tgp.view.scene.MainScene;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {

    public MainStage() {
        Scene mainScene = new MainScene();
        setTitle(TITLE);
        setScene(mainScene);
        show();
        setResizable(false);
        setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private static final String TITLE = "Time Tracker";
}
