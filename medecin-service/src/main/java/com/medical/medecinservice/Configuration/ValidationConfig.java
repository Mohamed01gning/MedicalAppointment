package com.medical.medecinservice.Configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig
{

    @Bean
    public Validator getValidator()
    {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}
