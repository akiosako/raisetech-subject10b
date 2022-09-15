package com.raisetech.crudsample.service;

import com.raisetech.crudsample.form.InsertForm;
import com.raisetech.crudsample.form.UpdateForm;
import com.raisetech.crudsample.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(int id);

    int insertUser(InsertForm insertForm);

    void updateUser(UpdateForm updateForm);

    void deleteUser(int id);
}
