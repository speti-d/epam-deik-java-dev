package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "Sign into an existing account")
    public String signInPrivileged(String username, String password) {
        Optional<User> user =  userService.singIn(username, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return String.format("Logged in as %s.", user.get().getUsername());
    }

    @ShellMethod(key = "sign out", value = "Sign out of currently logged in account")
    public void signOut() {
        userService.signOut();
    }

    @ShellMethod(key = "describe account", value = "Describe the currently logged in account")
    public String describeAccount() {
        Optional<User> user = userService.describe();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        return String.format("Signed in with privileged account '%s'", user.get().getUsername());
    }
}
