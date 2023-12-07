package ru.mirea.PrimeNumbers.services;

import ru.mirea.PrimeNumbers.dto.request.LoginRequest;
import ru.mirea.PrimeNumbers.dto.response.LoginResponse;
import ru.mirea.PrimeNumbers.dto.request.RegisterRequest;
import ru.mirea.PrimeNumbers.dto.response.RegisterResponse;

public interface PrimeUserService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
