package com.autodownload.util.runnable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import com.autodownload.util.Command;
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
    // Get api key
    var props = new Properties();
    var envFile = Paths.get(".env");
    try (var inputStream = Files.newInputStream(envFile)) {
      props.load(inputStream);
    } catch (IOException ignore) {}
    String apiKey = (String) props.get("API_KEY");
    try {
      String tmp = "/home/psikoo/Documents/GitHub/DGTCweb/java/images/"+cameraId+"tmp.jpg";
      String original = "/home/psikoo/Documents/GitHub/DGTCweb/java/images/"+cameraId+".jpg";
      Get.getImgFromURL(uriString, tmp);
      
      if(!MD5.getMD5(original).equals(MD5.getMD5(tmp))) {
        InputStream inputStream = new FileInputStream(tmp);
        Files.copy(inputStream, Paths.get(original), StandardCopyOption.REPLACE_EXISTING);
        Command.getInstance().executeCommand("./src/main/resources/scripts/post.sh ./images/"+cameraId+".jpg "+cameraId+" "+apiKey);
        //long unixTime = (System.currentTimeMillis()/1000L)-App.getStartTime();
        //System.out.println(unixTime+"> "+uriString+" ("+customDelay+")");
      }
    } catch (IOException | URISyntaxException e) {}
  }
}
