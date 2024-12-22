package com.example.trainix.controller;

import com.example.trainix.dto.*;
import com.example.trainix.entity.Users;
import com.example.trainix.service.impl.UserServiceImpl;
import com.example.trainix.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<UserApiResponse> createUser(@RequestBody UserDto userDto) {
        Users createUser = userService.createUser(userDto);
        return ResponseUtil.okay("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<GetAllUsersResponse>> getAllUsers(@RequestHeader("Authorization") String accessToken, @RequestParam(required = false) String name, @RequestParam(required = false) String role, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<GetAllUsersResponse> response = userService.getAllUsers(name, role, page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Users>> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        Users updateUser = userService.updateUser(id, updateUserDto);
        return ResponseUtil.updatedWithData(updateUser, "User updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserApiResponse> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseUtil.delete("User deleted successfully!!",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAllTrainers")
    public ResponseEntity<ApiResponse<List<Users>>> getAllTrainers() {
        List<Users> trainers = userService.getAllTrainers();
        return ResponseUtil.success(trainers,"Trainers retrieved successfully!!");
    }

}
