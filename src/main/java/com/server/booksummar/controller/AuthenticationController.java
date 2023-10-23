package com.server.booksummar.controller;

import com.server.booksummar.configuration.security.TokenService;
import com.server.booksummar.domain.User;
import com.server.booksummar.dto.request.AuthenticationRequest;
import com.server.booksummar.dto.request.RegisterRequest;
import com.server.booksummar.dto.response.LoginResponse;
import com.server.booksummar.dto.response.RegisterResponse;
import com.server.booksummar.mapper.RegisterMapper;
import com.server.booksummar.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RegisterMapper registerMapper;

    @PostMapping("/login")
    @Operation(summary = "Realiza o login de um usuário")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequest.login(), authenticationRequest.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            LoginResponse loginResponse = new LoginResponse(token, ((User) auth.getPrincipal()).getId());

            return ResponseEntity.ok(loginResponse);
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sua conta está desativada. Entre em contato com o suporte.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas. Verifique seu login e senha.");
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sua conta está bloqueada. Entre em contato com o suporte.");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação. Verifique suas credenciais.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha no login.");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registra um novo usuário")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            if (repository.findByLogin(registerRequest.getLogin()) != null) {
                throw new RuntimeException("Já existe um usuário com o login." + registerRequest.getLogin());
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.getPassword());
            User newUser = new User(registerRequest.getName(), registerRequest.getLogin(), registerRequest.getAvatarURL(), encryptedPassword, registerRequest.getRole());

            User savedUser = repository.save(newUser);

            RegisterResponse registerResponse = registerMapper.UserToRegisterResponse(savedUser);
            return ResponseEntity.ok(registerResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
