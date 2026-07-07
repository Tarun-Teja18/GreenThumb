package dev.tarun.greenthumb.service;

import dev.tarun.greenthumb.dto.AuthResponseDTO;
import dev.tarun.greenthumb.dto.LoginRequestDTO;
import dev.tarun.greenthumb.dto.RegisterRequestDTO;
import dev.tarun.greenthumb.dto.UserDTO;

public interface AuthService {
    UserDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}