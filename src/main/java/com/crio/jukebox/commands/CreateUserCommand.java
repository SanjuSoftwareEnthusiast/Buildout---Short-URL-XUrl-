package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.entities.User;

public class CreateUserCommand implements ICommand {

    private final IUserService userService;

    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userName = tokens.get(1);
        User user = userService.create(userName);
        System.out.println(user);
    }
    
}
