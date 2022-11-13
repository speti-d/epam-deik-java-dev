package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class AdminAccountCommand {

    private final UserService userService;

    @ShellMethod(key = "sign in privileged")
    public String signInPrivileged(String username, String password){
        Optional<User> user =  userService.singIn(username, password);
        if(user.isEmpty()){
            return "Login failed due to incorrect credentials";
        }
        return "";
    }

    @ShellMethod(key = "sign out")
    public void signOut(){
        userService.signOut();
    }

    @ShellMethod(key = "describe account")
    public String describeAccount(){
        Optional<User> user = userService.describe();
        if(user.isEmpty()) {
            return "You are not signed in";
        }
        return String.format("Signed in with privileged account '%s'", user.get().getUsername());
    }
}
