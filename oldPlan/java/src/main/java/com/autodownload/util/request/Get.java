package com.autodownload.util.request;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class Get {
  public static void getImgFromURL(String uriString, String destinationFile) throws IOException, URISyntaxException { //basic implementation for GET request that saves to the given file path
    URI uri = new URI(uriString);
    URL url = uri.toURL();
    OutputStream outputStream;
    byte[] b = new byte[2048];
    int length;
    try (InputStream is = url.openStream()) {
      outputStream = new FileOutputStream(destinationFile);
      while ((length = is.read(b)) != -1) {
        outputStream.write(b, 0, length);
      }
    }
    outputStream.close();
  }

}
