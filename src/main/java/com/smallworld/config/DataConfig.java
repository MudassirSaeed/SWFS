package com.smallworld.config;

import com.smallworld.data.IDataReader;
import com.smallworld.data.JsonDataReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {

    @Value("${file.name}")
    private String fileName;

    @Bean
    public <T> IDataReader<T> dataFetcher() {
        return new JsonDataReader<>(fileName);
    }
}
