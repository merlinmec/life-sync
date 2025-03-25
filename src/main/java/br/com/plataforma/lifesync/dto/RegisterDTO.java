package br.com.plataforma.lifesync.dto;

import br.com.plataforma.lifesync.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
