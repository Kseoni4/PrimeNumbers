package ru.mirea.PrimeNumbers.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.PrimeNumbers.dto.UserModel;
import ru.mirea.PrimeNumbers.dto.request.LoginRequest;
import ru.mirea.PrimeNumbers.dto.response.LoginResponse;
import ru.mirea.PrimeNumbers.dto.request.RegisterRequest;
import ru.mirea.PrimeNumbers.dto.response.RegisterResponse;
import ru.mirea.PrimeNumbers.entities.UserEntity;
import ru.mirea.PrimeNumbers.repositories.PrimeUsersRepository;
import ru.mirea.PrimeNumbers.security.jwt.JwtService;

import javax.security.auth.login.CredentialException;

@Service
@RequiredArgsConstructor
public class PrimeUserServiceImpl implements PrimeUserService {

    private final PrimeUsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();

        if(usersRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User is already register");
        }

        UserEntity user = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        user = usersRepository.save(user);

       UserModel userModel = UserModel.builder()
                                .email(user.getEmail())
                                .build();

       String jwt = jwtService.generateToken(userModel);

       return new RegisterResponse(user.getEmail(), jwt);
    }

    @Override
    @SneakyThrows
    public LoginResponse login(LoginRequest loginRequest) {
        if(usersRepository.findByEmail(loginRequest.getEmail()).isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity user = usersRepository.findByEmail(loginRequest.getEmail()).get();

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new CredentialException("Wrong username or password");
        }

        UserModel userModel = UserModel.builder()
                .email(user.getEmail())
                .build();

        String jwt = jwtService.generateToken(userModel);

        return new LoginResponse(userModel.getEmail(), jwt);
    }
}
