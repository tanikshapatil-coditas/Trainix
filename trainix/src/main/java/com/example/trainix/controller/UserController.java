package com.example.trainix.controller;

import com.example.trainix.dto.*;
import com.example.trainix.entity.Users;
import com.example.trainix.service.impl.UserServiceImpl;
import com.example.trainix.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
//@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserApiResponse> createUser(@RequestBody UserDto userDto) {
        Users createUser = userService.createUser(userDto);
        return ResponseUtil.okay("User created successfully", HttpStatus.CREATED);
    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<Page<GetAllUsersResponse>> getAllUsers(@RequestHeader("Authorization") String accessToken, @RequestParam(required = false) String name, @RequestParam(required = false) String role, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<GetAllUsersResponse> response = userService.getAllUsers(name, role, page, size);
        return ResponseEntity.ok(response);
    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Users>> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        Users updateUser = userService.updateUser(id, updateUserDto);
        return ResponseUtil.updatedWithData(updateUser, "User updated successfully");
    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserApiResponse> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseUtil.delete("User deleted successfully!!",HttpStatus.NO_CONTENT);
    }

//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getAllTrainers")
    public ResponseEntity<ApiResponse<List<Users>>> getAllTrainers() {
        List<Users> trainers = userService.getAllTrainers();
        return ResponseUtil.success(trainers,"Trainers retrieved successfully!!");
    }
}
