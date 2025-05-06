package com.autodownload;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.autodownload.lib.baringo.BaringoClient;
import com.autodownload.lib.baringo.model.Image;
import com.autodownload.lib.baringo.util.BaringoApiException;
import com.autodownload.util.request.Post;
import com.autodownload.util.runnable.ApiDownloader;
import com.autodownload.util.runnable.PhotoDownloader;

public class App {
  private static final long START_TIME = System.currentTimeMillis()/1000L;
  private static final int API_DELAY = 60;
  private static final String URI_STRING = "http://localhost:3000/api/cameras/";
  private static final Random RAND = new Random();
  private static final int DELAY_RAND = 10;
  private static final HashMap<Integer, ScheduledExecutorService> CAMERAS = new HashMap<>();
  
  private static int delay = 150;
  private static HashMap<Integer, String> watchList;

  public static long getStartTime() { return App.START_TIME; }
  public static int getDelay() { return App.delay; }
  public static void setWatchList(HashMap<Integer, String> watchList) { App.watchList = watchList; }

  public static void startApp(int delay) {
    // Setup App
    App.delay = delay;
    // Setup ApiDownloader
    startApiExecutor(URI_STRING);
    ApiDownloader.setup(URI_STRING);

    try {
      System.out.println(Post.postImage(
        "546c25a59c58ad7", 
        "https://api.imgur.com/3/image", 
        "/home/psikoo/Documents/GitHub/DGTCweb/java/images/598.jpg",
        "598",
        "https://infocar.dgt.es/etraffic/data/camaras/598.jpg"
      ));
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    BaringoClient client = null;
    String clientId = "PUT YOUR CLIENT ID HERE!";
    String clientSecret = "PUT YOUR CLIENT SECRET HERE!";
    try {
      client = new BaringoClient.Builder()
              .clientAuth( clientId, clientSecret )
              .build();
    } catch (BaringoApiException e) { e.printStackTrace(); } 

    try {
      Image image = client.imageService().uploadLocalImage(
        null, 
        "/home/psikoo/Documents/GitHub/DGTCweb/java/images/598.jpg", 
        "1", 
        "tittle", 
        "description");
      System.out.println( image );
    } catch (BaringoApiException | IOException e) { e.printStackTrace(); }
  }

  public static void updateCameras() {
    try {
      // Remove cameras that are not longer in the watchList
      for (Integer camera : CAMERAS.keySet()) {
        if(watchList.get(camera) == null) stopDownloaderExecutor(CAMERAS.get(camera), camera);
      }
      // Start downloader for all cameras in watchList
      for (Integer camera : watchList.keySet()) {
        if(CAMERAS.get(camera) == null) startDownloaderExecutor(watchList.get(camera), camera);
      }
    } catch(ConcurrentModificationException ignore) {}
  }

  private static void startApiExecutor(String uriString) {
    ScheduledExecutorService apiExecutor = Executors.newScheduledThreadPool(1);
    System.out.println("\n > Started autodownload from "+uriString+" with "+App.delay+"s of delay");
    apiExecutor.scheduleAtFixedRate(new ApiDownloader(uriString), 1, API_DELAY, TimeUnit.SECONDS);
  }

  private static void startDownloaderExecutor(String uriString, int cameraId) {
    int randomDelay = RAND.nextInt(DELAY_RAND);
    int customDelay = (randomDelay%2 == 0) ? App.delay+randomDelay :  App.delay-randomDelay;
    ScheduledExecutorService downloader = Executors.newScheduledThreadPool(1);
    downloader.scheduleAtFixedRate(new PhotoDownloader(uriString, cameraId, customDelay), 1, customDelay, TimeUnit.SECONDS);
    System.out.println(" + Downloader Started: "+cameraId);
    CAMERAS.put(cameraId, downloader);
  }

  private static void stopDownloaderExecutor(ScheduledExecutorService downloader, int cameraId) {
    downloader.shutdown();
    System.out.println(" - Downloader Stopped: "+cameraId);
    CAMERAS.remove(cameraId);
    updateCameras();
  }
}
