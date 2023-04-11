package com.github.zjor;

import com.github.zjor.repository.FileUserRepositoryImpl;
import com.github.zjor.repository.UserRepository;

public class App {

    public static void main(String[] args) {
        String filename = "./storage/users.json";
        UserRepository userRepository = new FileUserRepositoryImpl(filename);
        System.out.println(userRepository.findById(1));
        userRepository.create("Carol");
    }
}
