//package se331.project2.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import se331.project2.util.StorageFileDto;
//import se331.project2.util.SupabaseStorageService;
//
//@RestController
//@RequiredArgsConstructor
//public class SupabaseController {
//    final SupabaseStorageService supabaseStorageService;
//
//    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try{
//            if (file == null || file.isEmpty()) {
//                return ResponseEntity.badRequest().body("File is required.");//package se331.project2.controller;
/// /
/// /import lombok.RequiredArgsConstructor;
/// /import org.springframework.http.ResponseEntity;
/// /import org.springframework.web.bind.annotation.PostMapping;
/// /import org.springframework.web.bind.annotation.RequestParam;
/// /import org.springframework.web.bind.annotation.RestController;
/// /import org.springframework.web.multipart.MultipartFile;
/// /import se331.project2.util.StorageFileDto;
/// /import se331.project2.util.SupabaseStorageService;
/// /
/// /@RestController
/// /@RequiredArgsConstructor
/// /public class SupabaseController {
/// /    final SupabaseStorageService supabaseStorageService;
/// /
/// /    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
/// /    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
/// /        try{
/// /            if (file == null || file.isEmpty()) {
/// /                return ResponseEntity.badRequest().body("File is required.");
/// /            }
/// /            String fileUrl = supabaseStorageService.uploadFile(file);
/// /
/// /            return ResponseEntity.ok(fileUrl);
/// /        }catch (Exception e) {
/// /            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
/// /        }
/// /    }
/// /
/// /    @PostMapping("/uploadImage")
/// /    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
/// /        try{
/// /            StorageFileDto fileUrl = supabaseStorageService.uploadImage(file);
/// /
/// /            return ResponseEntity.ok(fileUrl);
/// /        }catch (Exception e) {
/// /            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
/// /        }
/// /    }
/// /}
//            }
//            String fileUrl = supabaseStorageService.uploadFile(file);
//
//            return ResponseEntity.ok(fileUrl);
//        }catch (Exception e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/uploadImage")
//    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
//        try{
//            StorageFileDto fileUrl = supabaseStorageService.uploadImage(file);
//
//            return ResponseEntity.ok(fileUrl);
//        }catch (Exception e) {
//            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
//        }
//    }
//}
