package ru.mirea.PrimeNumbers.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.PrimeNumbers.dto.request.PrimeRequest;
import ru.mirea.PrimeNumbers.services.PrimeService;

@RestController
@RequestMapping("/v1/prime")
@RequiredArgsConstructor
public class PrimeController {

    private final PrimeService primeService;

    // http://localhost:8095/v1/prime
    @PostMapping("")
    String checkPrime(@RequestBody PrimeRequest request){

        String[] array = request.getNumbers().split(", |,");

        int[] nums = new int[array.length];

        for(int i = 0; i < array.length; i++){
            nums[i] = Integer.parseInt(array[i]);
        }

        boolean isPrime = primeService.isPrime(nums);

        return "Число простое = "+isPrime;
    }

    @GetMapping("/num/{number}")
    Integer getNumber(@PathVariable int number){
        return number;
    }
}
