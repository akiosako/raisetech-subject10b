package com.raisetech.restapiexc.controller;

import com.raisetech.restapiexc.entity.InsertForm;
import com.raisetech.restapiexc.entity.UpdateForm;
import com.raisetech.restapiexc.entity.User;
import com.raisetech.restapiexc.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")//存在しないidを検索した場合エラーメッセージを返す
    public Optional<User> findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping//バリデーション:nameに@NotEmpty
    public ResponseEntity<String> insertUser(@RequestBody InsertForm insertForm) {
        userService.insertUser(insertForm);
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .path("/users/id").build().toUri();
        return ResponseEntity.created(uri).body("name successfully created");
    }


    @PatchMapping("/{id}")//バリデーション：nameが空文字、nullの場合エラーメッセージを返す
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable("id") int id, @RequestBody UpdateForm updateForm) {
        userService.updateUser(updateForm);
        return ResponseEntity.ok(Map.of("message", "name successfully updated"));
    }

    @DeleteMapping("/{id}")//存在しないidを指定した場合エラーメッセージを返す
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "name successfully deleted"));
    }


}



