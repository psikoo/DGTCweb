package com.autodownload.util.request;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Post {  
  public static String postImage(String clientID, String uriString, String imgPath, String tittle, String description) 
  throws IOException, InterruptedException {
    String b64 = new String(Base64.getEncoder().encode(Files.readAllBytes(Paths.get(imgPath))), StandardCharsets.UTF_8);
    String b64URL = URLEncoder.encode(b64, "UTF-8");
    Map<String, String> formData = new HashMap<>();
    //TODO figure out how the fuck to post images with formdata :c
    //https://www.baeldung.com/java-httpclient-post
    System.out.println("enc "+b64URL);
    formData.put("image", b64URL);
    formData.put("type", "image");
    formData.put("tittle", tittle);
    formData.put("description", description);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(uriString))
      .header("Content-Type", "application/x-www-form-urlencoded")
      .header("Authorization", "Client-ID "+clientID)
      .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
      .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      return response.body();
  }

  private static String getFormDataAsString(Map<String, String> formData) {
    StringBuilder formBodyBuilder = new StringBuilder();
    for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
      if (formBodyBuilder.length() > 0) {
        formBodyBuilder.append("&");
      }
      formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
      formBodyBuilder.append("=");
      formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
    }
    return formBodyBuilder.toString();
  }

  // curl --location 'https://api.imgur.com/3/image' \
  // --header 'Authorization: Client-ID 546c25a59c58ad7' \
  // --form 'image=@"/home/psikoo/Documents/GitHub/DGTCweb/java/images/598.jpg"' \
  // --form 'type="image"' \
  // --form 'title="REPLACEcameraIdREPLACE"' \
  // --form 'description="REPLACEcameraIdREPLACE"'
}
