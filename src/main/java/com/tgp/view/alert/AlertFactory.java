package com.tgp.view.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertFactory {

    public static void showErrorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Could not start program");
        StringWriter stackTraceWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTraceWriter));
        TextArea textArea = new TextArea(stackTraceWriter.toString());
        alert.getDialogPane().setExpandableContent(textArea);
        alert.showAndWait();
    }
}
