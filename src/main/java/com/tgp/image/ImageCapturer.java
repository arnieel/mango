package com.tgp.image;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageCapturer {

   public static boolean captureImage(String filename) {
      Webcam webcam = Webcam.getDefault();
      try {
         webcam.open();
         File file = new File(filename);
         file.getParentFile().mkdirs();
         ImageIO.write(webcam.getImage(), "PNG", file);
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      } finally {
         if (webcam != null) {
            webcam.close();
         }
      }
      return true;
   }
}
