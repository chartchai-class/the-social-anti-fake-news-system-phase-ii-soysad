package se331.project2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.project2.security.user.*;

@RestController
@RequestMapping("/api/v1/users") // ปรับปรุง URL ให้เป็นมาตรฐานเดียวกับ AuthController
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        // --- แก้ไขจุดที่ 1 ---
        // เปลี่ยนจาก UserMapper::toDTO เป็น UserMapper.INSTANCE::toUserDto
        Page<UserDTO> page = userService.findAll(pageable)
                .map(UserMapper.INSTANCE::toUserDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) return ResponseEntity.notFound().build();

        // --- แก้ไขจุดที่ 2 ---
        // เปลี่ยนจาก UserMapper.toDTO เป็น UserMapper.INSTANCE.toUserDto
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(user));
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')") // เพิ่มการป้องกัน ให้เฉพาะ Admin เท่านั้นที่เปลี่ยน Role ได้
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Integer id, @RequestBody UpdateRoleRequest request) {
        User updated = userService.updateRole(id, request.getRole());
        if (updated == null) {return ResponseEntity.notFound().build();}

        // --- แก้ไขจุดที่ 3 ---
        // เปลี่ยนจาก UserMapper.toDTO เป็น UserMapper.INSTANCE.toUserDto
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(updated));
    }
}