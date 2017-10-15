package com.tgp.view.stage;

import com.tgp.view.scene.ManageUsersScene;
import javafx.stage.Stage;

public class ManageUsersStage extends Stage {

    public ManageUsersStage() {
        setTitle(TITLE);
        setScene(new ManageUsersScene());
        show();
        setResizable(false);
        setOnCloseRequest(e -> new AdminStage());
    }

    private final String TITLE = "Manage Users";
}
