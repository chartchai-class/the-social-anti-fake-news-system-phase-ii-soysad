package se331.project2.controller;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import se331.project2.entity.Comment;
import se331.project2.security.user.UserService;
import se331.project2.service.CommentService;
import se331.project2.service.CommentServiceImpl;
import se331.project2.service.NewsService;
import se331.project2.util.StorageFileDto;
import se331.project2.util.SupabaseStorageService;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupabaseController {
    final SupabaseStorageService supabaseStorageService;
    final NewsService newsService;
    final CommentServiceImpl commentService;
    final UserService userService;


    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            StorageFileDto fileUrl = supabaseStorageService.uploadImage(file);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }



//    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
//    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file) {
//        try{
//            if (file == null || file.isEmpty()) {
//                return ResponseEntity.badRequest().body("File is required.");
//            }
//            StorageFileDto fileDto = supabaseStorageService.uploadImage(file);
//
//            return ResponseEntity.ok(fileDto);
//        }catch (Exception e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }

//    @PostMapping(value = "/news/{id}/mainImage/upload", consumes = "multipart/form-data")
//    public ResponseEntity<?> uploadImage(
//            @PathVariable Long id,
//            @RequestParam("image") MultipartFile file
//    ) {
//        try{
//            StorageFileDto imgUrl = supabaseStorageService.uploadImage(file);
//            newsService.setMainImage(id, imgUrl.getImgUrl());
//            return ResponseEntity.ok(imgUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }
//
//    @PostMapping(value = "/news/{id}/gallery/upload", consumes = "multipart/form-data")
//    public ResponseEntity<?> uploadGalleryImage(
//            @PathVariable Long id,
//            @RequestParam("image") List<MultipartFile> files
//    ) {
//        if (files == null || files.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        try{
//            List<String> urls = new ArrayList<>();
//            for (MultipartFile f : files) {
//                StorageFileDto imgUrl = supabaseStorageService.uploadImage(f); // ตรวจชนิดไฟล์ให้ด้วย
//                urls.add(imgUrl.getImgUrl());
//            }
//
//            newsService.addGalleryImages(id,urls);
//            return ResponseEntity.ok(urls);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }
//
//    //Comment Upload attachments
//    @PostMapping(value = "/comments/{id}/attachments/upload", consumes = "multipart/form-data")
//    public ResponseEntity<?> uploadAttachments(
//            @PathVariable Long id,
//            @RequestParam("image") List<MultipartFile> files
//    ) {
//        if (files == null || files.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        try{
//            List<String> urls = new ArrayList<>();
//            for (MultipartFile f : files) {
//                StorageFileDto imgUrl = supabaseStorageService.uploadImage(f);
//                urls.add(imgUrl.getImgUrl());
//            }
//
//            commentService.addAttachments(id,urls);
//            return ResponseEntity.ok(urls);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }
//
//
//    //User Upload ProfileImage//
//    @PostMapping(value = "/users/{id}/profileImage/upload", consumes = "multipart/form-data")
//    public ResponseEntity<?> uploadProfileImage(
//            @PathVariable Integer id,
//            @RequestParam("image") MultipartFile file) {
//        try{
//            StorageFileDto imgUrl = supabaseStorageService.uploadImage(file);
//            userService.setProfileImage(id, imgUrl.getImgUrl());
//            return ResponseEntity.ok(imgUrl);
//
//        }catch (Exception e){
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }
}
