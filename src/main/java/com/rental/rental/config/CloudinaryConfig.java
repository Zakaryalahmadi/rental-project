package com.rental.rental.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    Dotenv dotenv = Dotenv.load();

    private final String CLOUDINARY_URL = dotenv.get("CLOUDINARY_URL");

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(CLOUDINARY_URL);
    }
}
