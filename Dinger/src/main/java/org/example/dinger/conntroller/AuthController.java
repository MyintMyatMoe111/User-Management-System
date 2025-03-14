package org.example.dinger.conntroller;

import lombok.RequiredArgsConstructor;
import org.example.dinger.dto.LoginDto;
import org.example.dinger.dto.RegisterDto;
import org.example.dinger.entity.User;
import org.example.dinger.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        System.out.println("login===================:" + loginDto);
        String responseString = authService.login(loginDto);
        return ResponseEntity.ok(responseString);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String responseString = authService.register(registerDto);
        return ResponseEntity.ok(responseString);
    }

    @PostMapping("/approve/{userId}")
    public ResponseEntity<String> approveUser(@PathVariable Integer userId) {
        String approvedUser = authService.approveUser(userId);
        return ResponseEntity.ok(approvedUser);
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Integer userId) {
        String approvedUser = authService.blockUser(userId);
        return ResponseEntity.ok(approvedUser);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<User>> filterUsers(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String phonenumber,
            @RequestParam(required = false) String nrc,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Boolean approved,
            @RequestParam(required = false) Boolean blocked) {

        List<User> users = authService.filterUsers(userName, phonenumber, nrc, password, approved, blocked);
        return ResponseEntity.ok(users);
    }

}
