package br.com.plataforma.lifesync.controller;

import br.com.plataforma.lifesync.dto.AuthenticationDTO;
import br.com.plataforma.lifesync.dto.LoginResponseDTO;
import br.com.plataforma.lifesync.dto.RegisterDTO;
import br.com.plataforma.lifesync.model.User;
import br.com.plataforma.lifesync.repository.UserRepository;
import br.com.plataforma.lifesync.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody @Validated AuthenticationDTO data){
        if (this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body("Login is already in use");
        }
        var userPassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(userPassword);
        var token = this.tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody @Validated RegisterDTO data){
        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken (@RequestHeader("Authorization") String refreshToken) {
        String login = tokenService.validateToken(refreshToken);
        User user = (User) userRepository.findByLogin(login);
        String newToken = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(newToken));
    }
}
