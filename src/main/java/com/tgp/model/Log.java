package com.tgp.model;

import java.sql.Timestamp;

/**
 * Created by Arniel on 1/15/2017.
 */
public class Log {

   private int id;
   private int userId;
   private Timestamp time;
   private boolean in;
   private String imagePath;

   public boolean isIn() {
      return in;
   }

   public void setIn(boolean in) {
      this.in = in;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getId() {
      return id;
   }

   public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
   }

   public Timestamp getTime() {
      return time;
   }

   public void setTime(Timestamp time) {
      this.time = time;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public String getImagePath() {
      return imagePath;
   }
}
