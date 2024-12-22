package com.example.trainix.service;

import com.example.trainix.dto.GetAllUsersResponse;
import com.example.trainix.dto.UpdateUserDto;
import com.example.trainix.dto.UserDto;
import com.example.trainix.entity.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Users createUser(UserDto userDto);
    Page<GetAllUsersResponse> getAllUsers(String name,String role,int page,int size);
    Users updateUser(Long id, UpdateUserDto updateUserDto);
    void deleteUser(Long id);
    List<Users> getAllTrainers();
}
