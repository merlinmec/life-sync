package br.com.plataforma.lifesync.repository;

import br.com.plataforma.lifesync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);
}
