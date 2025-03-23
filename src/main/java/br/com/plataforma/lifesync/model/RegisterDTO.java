package br.com.plataforma.lifesync.model;

public record RegisterDTO(String login, String password, UserRole role) {
}
