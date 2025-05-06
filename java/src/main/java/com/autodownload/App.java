package com.autodownload;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.autodownload.util.runnable.ApiDownloader;
import com.autodownload.util.runnable.PhotoDownloader;

public class App {
  private static final long START_TIME = System.currentTimeMillis()/1000L;
  private static final int API_DELAY = 10;
  private static final String URI_STRING = "http://localhost:3000/api/cameras/";
  private static final Random RAND = new Random();
  private static final int DELAY_RAND = 10;
  private static final HashMap<Integer, ScheduledExecutorService> CAMERAS = new HashMap<>();
  

  private static int delay = 150;
  private static HashMap<Integer, String> watchList;

  public static long getStartTime() { return App.START_TIME; }
  public static int getDelay() { return App.delay; }
  public static void setWatchList(HashMap<Integer, String> watchList) { App.watchList = watchList; }

  public static ScheduledExecutorService apiExecutor;

  public static void startApp(int delay) {
    // Setup App
    App.delay = delay;
    // Setup ApiDownloader
    apiExecutor = startApiExecutor(URI_STRING);
    ApiDownloader.setup(URI_STRING);
  }

  public static void updateCameras() {
    System.out.println("# updating start?");
    // Remove cameras that are not longer in the watchList
    for (Integer camera : CAMERAS.keySet()) {
      System.out.println("checking "+watchList.get(camera));
      if(watchList.get(camera) == null) stopDownloaderExecutor(CAMERAS.get(camera), camera);
    }
    // Start downloader for all cameras in watchList
    for (Integer camera : watchList.keySet()) {
      if(CAMERAS.get(camera) == null) startDownloaderExecutor(watchList.get(camera), camera);
    }
  }

  private static ScheduledExecutorService startApiExecutor(String uriString) {
    apiExecutor = Executors.newScheduledThreadPool(1);
    System.out.println("\n > Started autodownload from "+uriString+" with "+App.delay+"s of delay");
    apiExecutor.scheduleAtFixedRate(new ApiDownloader(uriString), 1, API_DELAY, TimeUnit.SECONDS);
    return apiExecutor;
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
