package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandUtils {
    private final UserService userService;

    public Availability isAdmin() {
        return userService.isAdmin()
                ? Availability.available()
                : Availability.unavailable("you are not an Admin");
    }
}
