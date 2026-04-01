package org.example.bxbatuz;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class BxbatuzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BxbatuzApplication.class, args);
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        InputStream dbStream = getClass().getResourceAsStream("/GeoLite2-City.mmdb");
        if (dbStream == null) {
            throw new IOException("Database file not found in resources/geo/GeoLite2-City.mmdb");
        }
        return new DatabaseReader.Builder(dbStream).build();
    }
}
