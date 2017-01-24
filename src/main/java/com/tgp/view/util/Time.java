package com.tgp.view.util;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Time {

   public void activateTimer(final Label label) {
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
            Platform.runLater(() -> {
               Calendar rightNow = Calendar.getInstance();
               int hour = rightNow.get(Calendar.HOUR);
               int minute = rightNow.get(Calendar.MINUTE);
               int second = rightNow.get(Calendar.SECOND);
               String mark = rightNow.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
               int month = rightNow.get(Calendar.MONTH) + 1;
               int day = rightNow.get(Calendar.DAY_OF_MONTH);
               int year = rightNow.get(Calendar.YEAR);

               label.setText(String.format("Date: %02d/%02d/%s \t Time: %s:%02d:%02d %s", month, day, year, hour, minute, second, mark));
            });
         }
      }, 0, 1000);
   }

}
