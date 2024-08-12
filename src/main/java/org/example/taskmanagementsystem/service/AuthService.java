package org.example.taskmanagementsystem.service;


import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.Enums.RolesType;
import org.example.taskmanagementsystem.dto.RegisterRequest;
import org.example.taskmanagementsystem.dto.AuthRequest;
import org.example.taskmanagementsystem.dto.AuthResponse;
import org.example.taskmanagementsystem.model.Users;
import org.example.taskmanagementsystem.repository.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        var user = Users.builder()
                .name(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
         throw new UsernameNotFoundException("Username already exists");
        }
        if (!emailValidator(user.getEmail())) {
            throw new UsernameNotFoundException("Invalid email");
        }
        usersRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword()
                )
        );
        if (!emailValidator(authRequest.getUsername()))
        {
            throw new UsernameNotFoundException("Invalid email");
        }
        var user = usersRepository.findByEmail(authRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    private boolean emailValidator(String email) {
        String regex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
}
