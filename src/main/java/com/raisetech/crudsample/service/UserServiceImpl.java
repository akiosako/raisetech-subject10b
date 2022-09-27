package com.raisetech.crudsample.service;

import com.raisetech.crudsample.controller.exceptionhandler.ResourceNotFoundException;
import com.raisetech.crudsample.entity.User;
import com.raisetech.crudsample.form.InsertForm;
import com.raisetech.crudsample.form.UpdateForm;
import com.raisetech.crudsample.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> user = this.userMapper.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResourceNotFoundException("resource not found");
        }
    }

    @Override
    public int insertUser(InsertForm insertForm) {
        userMapper.insertUser(insertForm);
        return insertForm.getId();
    }

    @Override
    public void updateUser(UpdateForm updateForm) {
        userMapper.updateUser(updateForm);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }
}
