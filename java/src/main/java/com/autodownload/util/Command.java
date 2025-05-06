package com.autodownload.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Command {
  private static Command instance;

  private Command() {}
  public static Command getInstance() {
    if(instance == null) {
      instance = new Command();
    }
    return instance;
  }

  public void executeCommand(String command) {
    try {
      Process process = Runtime.getRuntime().exec(command);
      logOutput(process.getInputStream(), " * ");
      logOutput(process.getErrorStream(), "Error: ");
      process.waitFor();
    } catch (IOException | InterruptedException ignore) {}
  }

  private void logOutput(InputStream inputStream, String prefix) {
    new Thread(() -> {
      try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
        while (scanner.hasNextLine()) {
          synchronized (this) { System.out.println(prefix + scanner.nextLine()); }
        } 
      }
    }).start();
  }
}
