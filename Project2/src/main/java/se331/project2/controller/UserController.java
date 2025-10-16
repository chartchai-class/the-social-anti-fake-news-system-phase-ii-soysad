package se331.project2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se331.project2.security.user.*;
import se331.project2.util.StorageFileDto;
import se331.project2.util.SupabaseStorageService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;
    final SupabaseStorageService supabaseStorageService;


    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> page = userService.findAll(pageable)
                .map(UserMapper::toDTO);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Integer id, @RequestBody UpdateRoleRequest request) {
        User updated = userService.updateRole(id, request.getRole());
        if (updated == null) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(UserMapper.toDTO(updated));
    }


}
