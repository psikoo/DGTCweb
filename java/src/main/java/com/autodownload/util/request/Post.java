package com.autodownload.util.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Post {
  public static String postImage(String imgPath, String uriString) throws URISyntaxException, IOException, InterruptedException {
    Map<String, String> formData = new HashMap<>();
    formData.put("image", "baeldung");
    formData.put("message", "hello");

    // URL encode map
    String formString = formData.entrySet().stream()
      .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
      .collect(Collectors.joining("&"));

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(uriString))
      .POST(HttpRequest.BodyPublishers.ofString(formString))
      .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
