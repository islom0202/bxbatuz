package org.example.bxbatuz;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class BxbatuzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BxbatuzApplication.class, args);
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        File dbFile = new File("/home/ec2-user/app/GeoLite2-City.mmdb");
//        File dbFile = new File("D:\\java spring boot\\bxbatuz\\src\\main\\resources\\geo\\GeoLite2-City.mmdb");
        if (!dbFile.exists()) {
            throw new IOException("Database file not found: " + dbFile.getAbsolutePath());
        }
        return new DatabaseReader.Builder(dbFile).build();
    }
}
