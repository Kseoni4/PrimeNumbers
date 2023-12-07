package ru.mirea.PrimeNumbers.security.jwt;

import ru.mirea.PrimeNumbers.dto.UserModel;

public interface JwtService {

    String generateToken(UserModel userModel);

    UserModel parseToken(String jwt);
}
