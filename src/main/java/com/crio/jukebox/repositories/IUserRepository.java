package com.crio.jukebox.repositories;

import java.util.Optional;
import com.crio.jukebox.entities.User;

public interface IUserRepository {
    User saveUser(User user);
    public Optional<User> findById(String id);
}
