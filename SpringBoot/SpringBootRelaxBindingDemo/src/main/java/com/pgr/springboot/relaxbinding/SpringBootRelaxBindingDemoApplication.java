package com.pgr.springboot.relaxbinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pgr.springboot.relaxbinding.config.DatabaseConfig;

@SpringBootApplication
public class SpringBootRelaxBindingDemoApplication implements CommandLineRunner {

    @Autowired
    private DatabaseConfig databaseConfig;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRelaxBindingDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       
        System.out.println("Database Name: " + databaseConfig.getDatabaseName());
        System.out.println("User Name: " + databaseConfig.getUserName());
        System.out.println("Password: " + databaseConfig.getPassword());
      
 
    }

}
