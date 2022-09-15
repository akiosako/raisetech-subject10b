package com.raisetech.crudsample.service;

import com.raisetech.crudsample.form.InsertForm;
import com.raisetech.crudsample.form.UpdateForm;
import com.raisetech.crudsample.entity.User;
import com.raisetech.crudsample.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
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
    public Optional<User> findById(int id) {
        return userMapper.findById(id);
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
