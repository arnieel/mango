package com.tgp.view.stage;

import com.tgp.view.scene.AdminScene;
import javafx.stage.Stage;

public class AdminStage extends Stage {

    public AdminStage() {
        setTitle("Admin");
        setScene(new AdminScene());
        show();
        setOnCloseRequest(f -> new MainStage());
    }
}
