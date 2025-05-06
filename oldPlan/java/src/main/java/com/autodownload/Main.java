package com.autodownload;

public class Main {
  public static void main( String[] args ) {
    if((args.length == 2) && (args[0].equals("-delay") || args[0].equals("-d"))) {
      new App(Integer.parseInt(args[1]));
    } else {
      new App(150);
    }
  }
}
