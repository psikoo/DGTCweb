package com.autodownload.util.runnable;

import java.io.IOException;
import java.net.URISyntaxException;

import com.autodownload.App;
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
      //Get.getImgFromURL(uriString, "/home/psikoo/Documents/GitHub/DGTCweb/java/images/"+cameraId+"-"+System.currentTimeMillis()/1000L+".jpg");
      Get.getImgFromURL(uriString, "/home/psikoo/Documents/GitHub/DGTCweb/java/images/"+cameraId+".jpg");
    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    }
    System.out.println(unixTime+"> "+uriString+" ("+customDelay+")");
  }
}
