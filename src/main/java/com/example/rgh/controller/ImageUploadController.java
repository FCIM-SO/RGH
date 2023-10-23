package com.example.rgh.controller;

import com.example.rgh.auth.AuthenticationResponse;
import com.example.rgh.entity.ImageModel;
import com.example.rgh.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/image")
@CrossOrigin
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/upload")
    public ResponseEntity<AuthenticationResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                                    Principal principal) throws IOException {

        imageUploadService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new AuthenticationResponse("Image Uploaded Successfully"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{postId}/upload")
    public ResponseEntity<AuthenticationResponse> uploadImageToPost(@PathVariable("postId") String postId,
                                                                    @RequestParam("file") MultipartFile file,
                                                                    Principal principal) throws IOException {
        imageUploadService.uploadImageToPost(file, principal, Long.parseLong(postId));
        return ResponseEntity.ok(new AuthenticationResponse("Image Uploaded Successfully"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel userImage = imageUploadService.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{postId}/image")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable("postId") String postId) {
        ImageModel postImage = imageUploadService.getImageToPost(Long.parseLong(postId));
        return new ResponseEntity<>(postImage, HttpStatus.OK);
    }

}
