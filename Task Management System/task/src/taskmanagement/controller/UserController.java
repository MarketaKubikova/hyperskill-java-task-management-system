package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskmanagement.model.UserRequestDto;
import taskmanagement.service.UserService;

@RestController
@RequestMapping("/api/accounts")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequestDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok().build();
    }
}
