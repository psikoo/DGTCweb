package com.autodownload;

public class PhotoDownloader implements Runnable {
  private int camera = 0;
  public int getCamera() { return this.camera; }

  public PhotoDownloader(int camera) {
    this.camera = camera;
  }

  @Override
  public void run() {
    System.out.println("womp "+camera);
  }
}
