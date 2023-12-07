package ru.mirea.PrimeNumbers.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.PrimeNumbers.dto.request.LoginRequest;
import ru.mirea.PrimeNumbers.dto.request.RegisterRequest;
import ru.mirea.PrimeNumbers.dto.response.LoginResponse;
import ru.mirea.PrimeNumbers.dto.response.RegisterResponse;
import ru.mirea.PrimeNumbers.services.PrimeUserService;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final PrimeUserService primeUserService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest){
        return primeUserService.register(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse servletResponse){
        LoginResponse loginResponse = primeUserService.login(loginRequest);
        servletResponse.addCookie(new Cookie("jwt", loginResponse.getJwt()));
        return loginResponse;
    }

}
