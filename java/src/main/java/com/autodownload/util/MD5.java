package com.autodownload.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
  public static String getMD5(String path) {
    try (FileInputStream fileStream = new FileInputStream(path)) {
      MessageDigest md = MessageDigest.getInstance("MD5"); 
      byte[] buffer = new byte[1024];

      int n;  
      while ((n = fileStream.read(buffer)) != -1) {  
        md.update(buffer, 0, n);  
      }
      byte[] digest = md.digest(); 
      StringBuilder sb = new StringBuilder();  
      for (byte b : digest) {  
        sb.append(String.format("%02x", b & 0xff));  
      }

      fileStream.close();
      return sb.toString();  
    } 
    catch (FileNotFoundException | NoSuchAlgorithmException ignore) {}
    catch (IOException ignore) {}
    return "";
  }
}
