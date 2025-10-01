package com.group5.firstpassport;

import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.Role;
import com.group5.firstpassport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FirstPassportApplication {

  public static void main(String[] args) {
    SpringApplication.run(FirstPassportApplication.class, args);
  }
}
