package com.raisetech.crudsample.service;

import com.raisetech.crudsample.entity.User;
import com.raisetech.crudsample.form.InsertForm;
import com.raisetech.crudsample.form.UpdateForm;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    int insertUser(InsertForm insertForm);

    void updateUser(UpdateForm updateForm);

    void deleteUser(int id);
}
