package com.server.booksummar.service;

import com.server.booksummar.domain.User;
import com.server.booksummar.dto.request.UserRequest;
import com.server.booksummar.dto.response.UserResponse;
import com.server.booksummar.mapper.UserMapper;
import com.server.booksummar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserResponse create(UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(UUID idUser) {
        if(!userRepository.findById(idUser).isPresent()){
            throw new NoSuchElementException("No User was found with the specified Id.");
        } else {
            User user = userRepository.findById(idUser).get();
            UserResponse userResponse = userMapper.userToUserResponse(user);
            return userResponse;
        }
    }

    public UserResponse update(UserRequest userRequest, UUID idUser) {
        if(!userRepository.findById(idUser).isPresent()){
            throw new NoSuchElementException("No User was found with the specified Id.");
        } else {
            User user = userRepository.findById(idUser).get();
            userMapper.userUpdate(userRequest, user);
            userRepository.save(user);
            UserResponse userResponse = userMapper.userToUserResponse(user);

            return userResponse;
        }
    }

    public void delete(UUID idUser) {
        if(!userRepository.findById(idUser).isPresent()){
            throw new NoSuchElementException("No User was found with the specified Id.");
        } else {
            userRepository.deleteById(idUser);
        }
    }
}