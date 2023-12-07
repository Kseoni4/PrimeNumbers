package ru.mirea.PrimeNumbers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.PrimeNumbers.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrimeUsersRepository extends JpaRepository<UserEntity, UUID> {
    /*
        SELECT *
        FROM   prime_users
        WHERE  email = email
     */
    Optional<UserEntity> findByEmail(String email);
}
