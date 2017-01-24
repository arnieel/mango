package com.tgp.view.stage;

import com.tgp.view.scene.AdminScene;
import javafx.stage.Stage;

/**
 * Created by Arniel on 1/15/2017.
 */
public class AdminStage extends Stage {

   public AdminStage() {
      setTitle("Admin");
      setScene(new AdminScene());
      show();
      setOnCloseRequest(f -> new MainStage());
   }
}
