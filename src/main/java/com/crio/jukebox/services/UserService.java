package com.crio.jukebox.services;

import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.entities.User;

public class UserService implements IUserService{

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User create(String name) {
        User user= new User(name);
        User userwithID = userRepository.saveUser(user);
        return userwithID;
    }
    
}
