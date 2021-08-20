package com.stan.thymeleafapp;

import com.stan.thymeleafapp.user.User;
import com.stan.thymeleafapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddNewUser(){
        User user = new User();
        user.setEmail("alicia@gmail.com");
        user.setPassword("123456");
        user.setFirstName("Alicia");
        user.setLastName("Zulu");

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindAllUsers(){
        Iterable<User> users = userRepository.findAll();

        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for( User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindUserById(){
        int userID = 1;
        Optional<User> optionalUser = userRepository.findById(userID);
        Assertions.assertThat(optionalUser).isPresent();

        System.out.println(optionalUser.get());
    }

    @Test
    public void testUpdateUser(){
        int userID = 1;
        Optional<User> optionalUser = userRepository.findById(userID);
        User user = optionalUser.get();
        user.setLastName("Mgaga");
        userRepository.save(user);
        User updatedUser = userRepository.findById(userID).get();

        Assertions.assertThat(updatedUser.getLastName()).isEqualTo("Mgaga");
    }

    @Test
    public void testDeleteUser(){
        int userID = 2;
        userRepository.deleteById(userID);

        Optional<User> optionalUser = userRepository.findById(userID);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
