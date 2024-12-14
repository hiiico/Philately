package com.philately.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.philately.model.dto.RegisterDTO;
import com.philately.model.dto.UserDTO;
import com.philately.model.entity.User;
import com.philately.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import com.philately.util.LoggedUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final LoggedUser loggedUser;
    private final HttpSession session;


    private final Gson gson;

    private final ModelMapper modelMapper;

    private static final String USERS_FILE_PATH = "src/main/resources/static/files/users.json";

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder encoder, LoggedUser loggedUser, HttpSession session, Gson gson, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.loggedUser = loggedUser;
        this.session = session;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    public String readUsersFileContent() throws IOException {
        return Files.readString(Path.of(USERS_FILE_PATH));
    }

    public UserDTO findUserByUsername(String username) {
        User user = this.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        return this.mapUserDTO(user);
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }

        return this.mapUserDTO(user);
    }

    public boolean checkCredentials(String username, String password) {
        User user = this.getUserByUsername(username);

        if (user == null) {
            return false;
        }

        return encoder.matches(password, user.getPassword());
    }

    public void login(String username) {
        User user = this.getUserByUsername(username);
        this.loggedUser.setId(user.getId());
        this.loggedUser.setUsername(user.getUsername());
    }

    public void register(RegisterDTO registerDTO) {
        this.userRepo.save(this.mapUser(registerDTO));
        this.login(registerDTO.getUsername());
    }

    public void logout() {
        this.session.invalidate();
        this.loggedUser.setId(null);
        this.loggedUser.setUsername(null);
    }

    private User getUSerById(Long userId) {
        return this.userRepo.findById(userId).orElseThrow();
    }

    private User getUserByUsername(String username) {
        return this.userRepo.findByUsername(username).orElse(null);
    }

    private User mapUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        return user;
    }

    private UserDTO mapUserDTO(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setUsername(user.getUsername());
    }

    public void initUsers() throws IOException {
        Arrays.stream(gson.fromJson(readUsersFileContent(), UserDTO[].class))
                .map(userDTO -> {
                    User user = modelMapper.map(userDTO, User.class);
                    user.setPassword(encoder.encode(userDTO.getPassword()));
                    return user;
                }).forEach(userRepo::save);
    }

//    public void initAdmin() {
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword(encoder.encode("1234"));
//        admin.setEmail("admin@abv.bg");
//        userRepo.save(admin);
//    }
//    public void initTest() {
//        User test = new User();
//        test.setUsername("testUser");
//        test.setPassword(encoder.encode("1234"));
//        test.setEmail("test@abv.bg");
//        userRepo.save(test);
//    }

    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);

    }
}
