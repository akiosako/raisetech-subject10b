package com.raisetech.crudsample.controller;

import com.raisetech.crudsample.controller.exceptionhandler.ResourceNotFoundException;
import com.raisetech.crudsample.entity.User;
import com.raisetech.crudsample.form.InsertForm;
import com.raisetech.crudsample.form.UpdateForm;
import com.raisetech.crudsample.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/users")
@RestController
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(
            ResourceNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }


    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")//文字列、存在しないidを指定すると404を返す
    public User findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping//バリデーションエラーが発生した場合は、MethodArgumentNotValidExceptionがスローされる。
    public ResponseEntity<String> insertUser(@RequestBody @Validated InsertForm insertForm, UriComponentsBuilder
            uriComponentsBuilder) {
        int newId = userService.insertUser(insertForm);
        URI uri = uriComponentsBuilder.path("users/{id}").buildAndExpand(newId).toUri();
        return ResponseEntity.created(uri).body("name successfully created");
    }


    @PatchMapping("/{id}")//存在しないidに成功レスポンス×、文字列を入れた場合400が返る。nameがnull、空文字だと400、スペースだと400
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable int id,
                                                          @RequestBody @Validated UpdateForm updateForm) {
        updateForm.setId(id);
        userService.updateUser(updateForm);
        return ResponseEntity.ok(Map.of("message", "name successfully updated"));
    }

    @DeleteMapping("/{id}")//存在しないid成功レスポンス×,文字列を入れると400,
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "name successfully deleted"));
    }
}




