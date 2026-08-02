package com.foody.delivery.controller;

import com.foody.delivery.database.repository.UserRepository;
import com.foody.delivery.domain.user.AuthDTO;
import com.foody.delivery.domain.user.LoginResponseDTO;
import com.foody.delivery.domain.user.RegisterDTO;
import com.foody.delivery.domain.user.User;
import com.foody.delivery.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody @Validated AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getUser_id()));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated RegisterDTO data) {
        if(this.userRepository.findByEmail(data.email()).isPresent()) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}