package com.docencia.rest.service.interfaces;

import com.docencia.rest.exeption.ResourceNotFoundException;
import com.docencia.rest.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserServiceInterface {
    public List<User> getAllUsers();
    public User getUserById(@PathVariable(value = "id") int userId) throws ResourceNotFoundException;
    public User createUser(@Validated @RequestBody User user);
    public User updateUser(@PathVariable(value = "id") int userId, @Validated @RequestBody User userDetails) throws ResourceNotFoundException;
    public void deleteUser(@PathVariable(value = "id") int userId) throws ResourceNotFoundException;
}
