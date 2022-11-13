package com.epam.training.ticketservice.core.user;

import java.util.Optional;

public interface UserService {

    Optional<User> singIn(String username, String password);

    Optional<User> signOut();

    Optional<User> describe();
}
