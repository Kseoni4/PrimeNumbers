package ru.mirea.PrimeNumbers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.PrimeNumbers.entities.PrimeNumberEntity;
import ru.mirea.PrimeNumbers.repositories.PrimeNumberRepository;

@Service
@RequiredArgsConstructor
public class PrimeServiceImpl implements PrimeService {

    private final PrimeNumberRepository numberRepository;

    @Override
    public boolean isPrime(int[] numbers) {

        int sum = 0;
        for(int n : numbers){
            sum += n;
        }

        /**
         *  SELECT *
         *  FROM   prime_numbers
         *  WHERE  id = sum;
         */
        if(numberRepository.findById(sum).isPresent()){
            return true;
        }

        // Check if number is less than
        // equal to 1
        if (sum <= 1)
            return false;

            // Check if number is 2
        else if (sum == 2)
            return true;

            // Check if n is a multiple of 2
        else if (sum % 2 == 0)
            return false;

        // If not, then just check the odds
        for (int i = 3; i <= Math.sqrt(sum); i += 2) {
            if (sum % i == 0)
                return false;
        }

        /**
         *  INSERT INTO prime_numbers (id, number)
         *  VALUES      (sum, sum);
         */
        numberRepository.save(new PrimeNumberEntity(sum, sum));

        return true;

    }

    @Override
    public boolean isPrime(int number) {
        return numberRepository.existsById(number);
    }
}
