package com.autodownload.util.runnable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.autodownload.App;
import com.autodownload.util.request.Get;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiDownloader implements Runnable {
  private String uriString = "";
  private static HashMap<Integer, String> watchList;

  public ApiDownloader(String uriString) {
    this.uriString = uriString;
  }

  @Override
  public void run() {
    watchList = new HashMap<>();
    try {
      JsonNode fullJson = Get.getJsonFromURL(uriString);

      int count = 0;
      while(count >= 0) {
        // filter json and add to watchList
        if(fullJson.get(count).get("watch").asText().equals("true")) {
          Integer name = fullJson.get(count).get("name").asInt();
          String url = fullJson.get(count).get("url").asText();
          watchList.put(name, url);
        }
        // loop until there are no more nodes
        if(fullJson.get(count+1).get("id") == null) count = -1;
        else count++;
      }
    } catch (URISyntaxException | IOException e) { e.printStackTrace(); 
    } catch (NullPointerException ignore) {}
    App.setWatchList(watchList);
    App.updateCameras(); //! breaks when setting of 2 cameras in 1 update
    long unixTime = (System.currentTimeMillis()/1000L)-App.getStartTime();
    System.out.println(unixTime+"> Updated cameras");
  }

  public static void setup(String uriString) {
    watchList = new HashMap<>();
    try {
      JsonNode fullJson = Get.getJsonFromURL(uriString);
      
      int count = 0;
      while(count >= 0) {
        // filter json and add to watchList
        if(fullJson.get(count).get("watch").asText().equals("true")) {
          Integer name = fullJson.get(count).get("name").asInt();
          String url = fullJson.get(count).get("url").asText();
          watchList.put(name, url);
        }
        // loop until there are no more nodes
        if(fullJson.get(count+1).get("id") == null) count = -1;
        else count++;
      }
    } catch (URISyntaxException | IOException e) { e.printStackTrace(); 
    } catch (NullPointerException ignore) {}
    App.setWatchList(watchList);
    App.updateCameras();
  }
}
