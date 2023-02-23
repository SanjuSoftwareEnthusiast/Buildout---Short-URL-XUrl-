package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<String,User> userMap;
    private Integer autoIncrement = 0;

    public UserRepository(){
        userMap = new HashMap<String,User>();
    }

    public UserRepository(Map<String,User> userMap) {
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public User saveUser(User user) {
        if(user.getId() == null){
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement),user.getName());
            userMap.put(u.getId(),u);
            return u;
        }
        userMap.put(user.getId(),user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }
    
}
