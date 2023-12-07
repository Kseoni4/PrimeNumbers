package ru.mirea.PrimeNumbers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "prime_users")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String email;

    private String password;


}
