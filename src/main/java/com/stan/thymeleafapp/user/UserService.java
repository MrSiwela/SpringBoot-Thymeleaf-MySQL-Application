package com.stan.thymeleafapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUser(){
        return (List<User>) userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getByID(int id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UserNotFoundException("Could not find any user with ID :"+id);
    }

    public void deleteUser(int id) throws UserNotFoundException {
        Long count = userRepository.countById(id);
        if(count == null || count ==0){
            throw new UserNotFoundException("Could not find any user with ID :"+id);
        }
        userRepository.deleteById(id);
    }
}
