package com.autodownload.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Get {
  // Fetch an image from the given url and downloads it to the given path
  public static void getImgFromURL(String uriString, String destinationFile) throws URISyntaxException, IOException {
    URL url = new URI(uriString).toURL();
    try (InputStream inputStream = url.openStream()) {
      try {
        Files.copy(inputStream, Paths.get(destinationFile));
      } catch (FileAlreadyExistsException e) {
        Files.copy(inputStream, Paths.get(destinationFile), StandardCopyOption.REPLACE_EXISTING);
      }
    }  
  }

  // Fetch json object
  public static JsonNode getJsonFromURL(String uriString) throws URISyntaxException, IOException {
    String[] command = {"./request/get.sh"};
    Command.instance().executeCommand(command);
    URL url = new File("./request/cams.json").toURI().toURL();
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(url);
  }
}
