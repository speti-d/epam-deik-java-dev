package com.epam.training.ticketservice.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User loggedInUser;

    @Override
    public Optional<User> singIn(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = user.get();
        return user;
    }

    @Override
    public void signOut() {
        loggedInUser = null;
    }

    @Override
    public Optional<User> describe() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public boolean isAdmin() {
        if (Optional.ofNullable(loggedInUser).isPresent()) {
            return loggedInUser.getRole() == User.Role.ADMIN;
        }
        return false;
    }
}
