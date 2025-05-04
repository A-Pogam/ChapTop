package com.chatop.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

  @Value("${file.upload-dir}")
  private String uploadDir;

  @GetMapping("/uploads/{filename:.+}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    try {
      Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists()) {
        System.out.println("Trying to access file: " + filePath.toString());
        return ResponseEntity.ok()
          .contentType(MediaType.IMAGE_JPEG)
          .body(resource);
      } else {
        return ResponseEntity.notFound().build();

      }

    } catch (MalformedURLException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
