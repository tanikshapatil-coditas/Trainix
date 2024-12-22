package com.example.trainix.service.impl;

import com.example.trainix.dto.GetAllUsersResponse;
import com.example.trainix.dto.UpdateUserDto;
import com.example.trainix.dto.UserDto;
import com.example.trainix.entity.Role;
import com.example.trainix.entity.TechStack;
import com.example.trainix.entity.Users;
import com.example.trainix.exception.UsernameExistsException;
import com.example.trainix.mapper.UsersMapper;
import com.example.trainix.repository.RoleRepository;
import com.example.trainix.repository.TechStackRepository;
import com.example.trainix.repository.UserRepository;
import com.example.trainix.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TechStackRepository techStackRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users createUser(UserDto userDto) {

        userRepository.findByEmail(userDto.getEmail()).ifPresent(users -> {
            throw new UsernameExistsException();
        });

        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        if (userDto.getLocation() == null) {
            throw new RuntimeException("Location must be provided");
        }
        user.setLocation(userDto.getLocation());
        user.setProfilePic(userDto.getProfilePic());
        String randomPassword = generateRandomPassword(8);
        user.setPassword(passwordEncoder.encode(randomPassword));

        Set<Role> roles = new HashSet<>();
        Role studentRole = roleRepository.findByName("STUDENT").orElseThrow(() -> new NoSuchElementException("Role 'STUDENT' not found"));
        if (studentRole != null) {
            roles.add(studentRole);
        }
        if (userDto.getIsAdmin()) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new NoSuchElementException("Role 'ADMIN' not found"));
            if (adminRole != null) {
                roles.add(adminRole);
            }
        }
        user.setRoleSet(roles);

        Set<TechStack> techStackSet = new HashSet<>();
        for (String i : userDto.getTechStack()) {
            TechStack techStack = techStackRepository.findByName(i.toUpperCase()).orElseThrow(() -> new NoSuchElementException("Tech Stack '" + techStackSet + "' not found"));
            techStackSet.add(techStack);
        }
        user.setTechStackSet(techStackSet);
        userRepository.save(user);
        String roleNamesString = user.getRoleSet().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
        sendRandomPasswordEmail(user, roleNamesString, randomPassword);
        return user;
    }


    public void sendRandomPasswordEmail(Users user, String roleSet, String randomPassword) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(user.getEmail());
            helper.setSubject("Your New Account Password");
            helper.setText(String.format(
                    "Dear %s,\n\nYour account has been created with the role: %s.\nYour temporary password is: %s\n",
                    user.getFirstName().concat(" ").concat(user.getLastName()), roleSet, randomPassword
            ));

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }

    //2.Get All users
    @Override
    public Page<GetAllUsersResponse> getAllUsers(String name, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> usersPage = userRepository.findAllByNameAndRole(name, role, pageable);
        return usersPage.map(usersMapper::convertToDto);
    }

    //3.Update users
    @Override
    public Users updateUser(Long id, UpdateUserDto updateUserDto) {
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exist"));
        if (users.getIsDeleted()) {
            throw new RuntimeException("User has been deleted and cannot be updated.");
        }
        users.setFirstName(updateUserDto.getFirstName());
        users.setLastName(updateUserDto.getLastName());
        users.setEmail(updateUserDto.getEmail());
        users.setLocation(updateUserDto.getLocation());
        Set<TechStack> techStackSet = new HashSet<>();
        for (String i : updateUserDto.getTechStacks()) {
            TechStack techStack = techStackRepository.findByName(i.toUpperCase()).orElseThrow(() -> new NoSuchElementException("Tech Stack '" + techStackSet + "' not found"));
            techStackSet.add(techStack);
        }
        users.setTechStackSet(techStackSet);
        userRepository.save(users);
        return users;
    }

    //4.Delete user
    @Override
    public void deleteUser(Long id) {
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        if (users.getIsDeleted()) {
            throw new RuntimeException("User has been deleted and cannot be updated.");
        }
        users.setIsDeleted(true);
        userRepository.save(users);
    }

    //5.Get all trainers
    @Override
    public List<Users> getAllTrainers() {
        return userRepository.findAllTrainers();
    }

}
