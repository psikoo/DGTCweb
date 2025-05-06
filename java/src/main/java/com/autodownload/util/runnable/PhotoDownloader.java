package com.autodownload.util.runnable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.autodownload.App;
import com.autodownload.util.MD5;
import com.autodownload.util.request.Get;

public class PhotoDownloader implements Runnable {
  private String uriString = "";
  private int customDelay = 0;
  private int cameraId = 0;

  public PhotoDownloader(String uriString, int cameraId, int customDelay) {
    this.uriString = uriString;
    this.cameraId = cameraId;
    this.customDelay = customDelay;
  }

  @Override
  public void run() {
    long unixTime = (System.currentTimeMillis()/1000L)-App.getStartTime();
    try {
      String tmp = "/home/psikoo/Documents/GitHub/DGTCweb/java/images/"+cameraId+"tmp.jpg";
      String original = "/home/psikoo/Documents/GitHub/DGTCweb/java/images/"+cameraId+".jpg";
      Get.getImgFromURL(uriString, tmp);
      
      if(!MD5.getMD5(original).equals(MD5.getMD5(tmp))) {
        InputStream inputStream = new FileInputStream(tmp);
        Files.copy(inputStream, Paths.get(original), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(unixTime+"> "+uriString+" ("+customDelay+")");
      }
    } catch (IOException | URISyntaxException e) {}
  }
}
