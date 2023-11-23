package ru.mirea.PrimeNumbers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.PrimeNumbers.entities.PrimeNumberEntity;

@Repository
public interface PrimeNumberRepository extends JpaRepository<PrimeNumberEntity, Integer> {
}
