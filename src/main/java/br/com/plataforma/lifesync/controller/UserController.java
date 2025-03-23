package br.com.plataforma.lifesync.controller;

import br.com.plataforma.lifesync.model.User;
import br.com.plataforma.lifesync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<User> getAll(){
        return this.userRepository.findAll();
    }
}
