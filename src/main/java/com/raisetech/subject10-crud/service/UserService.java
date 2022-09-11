package com.raisetech.restapiexc.service;

import com.raisetech.restapiexc.entity.InsertForm;
import com.raisetech.restapiexc.entity.UpdateForm;
import com.raisetech.restapiexc.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(int id);

    void insertUser(InsertForm insertForm);

    void updateUser(UpdateForm updateForm);

    void deleteUser(int id);
}
