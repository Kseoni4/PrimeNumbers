package ru.mirea.PrimeNumbers.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
@AllArgsConstructor
@NoArgsConstructor
public class TokenProperties {

    private String key;

    private long ttl;

}
