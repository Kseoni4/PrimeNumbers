package ru.mirea.PrimeNumbers.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mirea.PrimeNumbers.dto.UserModel;
import ru.mirea.PrimeNumbers.entities.UserEntity;
import ru.mirea.PrimeNumbers.repositories.PrimeUsersRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PrimeUsersRepository primeUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = primeUsersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return UserModel.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
