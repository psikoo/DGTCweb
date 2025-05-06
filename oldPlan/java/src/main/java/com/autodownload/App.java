package com.autodownload;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.autodownload.util.request.Get;

public class App {
  private int delay = 150;
  private Dictionary<Integer, ScheduledExecutorService> downloaderCamera;

  public App(int delay) {
    System.out.println("\n > Started autodownload with a delay of "+delay+" seconds");
    this.delay = delay;
    startApp();
  }

  private void startApp() {
    downloaderCamera = new Hashtable<>();
    try {
      Get.getJsonFromURL("http://localhost:3000/api/cameras/");
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }

    startDownloader(1);
    startDownloader(2);
    startDownloader(3);
    startDownloader(4);
    startDownloader(5);
    startDownloader(6);
    stopDownloader(3);
  }

  private void startDownloader(int camera) {
    ScheduledExecutorService downloader = Executors.newScheduledThreadPool(1);
    downloader.scheduleAtFixedRate(new PhotoDownloader(camera), 1, this.delay, TimeUnit.SECONDS);
    System.out.println(" + Downloader Started: "+camera);
    downloaderCamera.put(camera, downloader);
  }

  private void stopDownloader(int camera) {
    downloaderCamera.get(camera).shutdown();
    System.out.println(" - Downloader Stopped: "+camera);
    downloaderCamera.remove(camera);
  }






}
