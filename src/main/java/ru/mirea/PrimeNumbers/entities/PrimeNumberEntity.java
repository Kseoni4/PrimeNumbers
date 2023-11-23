package ru.mirea.PrimeNumbers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prime_numbers")
@AllArgsConstructor
@NoArgsConstructor
public class PrimeNumberEntity {

    @Id
    private int id;

    @Column
    private int primeNumber;

}
