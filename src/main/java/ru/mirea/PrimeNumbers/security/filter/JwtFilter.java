package ru.mirea.PrimeNumbers.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mirea.PrimeNumbers.dto.UserModel;
import ru.mirea.PrimeNumbers.repositories.PrimeUsersRepository;
import ru.mirea.PrimeNumbers.security.jwt.JwtService;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final PrimeUsersRepository primeUsersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = getToken(request);

        if(Objects.isNull(jwt)) {
            filterChain.doFilter(request, response);
            log.info("no jwt in header");
            return;
        }

        UserModel userModel = jwtService.parseToken(jwt);

        if(Objects.isNull(userModel)) {
            filterChain.doFilter(request, response);
            log.info("no user in jwt");
            return;
        }

        if(primeUsersRepository.findByEmail(userModel.getEmail()).isEmpty()){
            filterChain.doFilter(request, response);
            log.info("no user in db");
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getAuthorities()
        );

        log.info("Create security context");

        SecurityContext context = SecurityContextHolder.getContext();

        context.setAuthentication(authenticationToken);

        log.info("Set authentication");

        filterChain.doFilter(request, response);
    }


    private String getToken(HttpServletRequest request) {

        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")) {
                    log.info("get jwt from cookie");
                    return cookie.getValue();
                }
            }
        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            return null;
        }

        final String[] strs = header.split(" ");
        if (strs.length != 2) {
            return null;
        }

        return strs[1].trim();
    }
}
