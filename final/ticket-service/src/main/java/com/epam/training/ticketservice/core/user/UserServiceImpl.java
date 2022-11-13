package com.epam.training.ticketservice.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private User loggedInUser;

    @Override
    public Optional<User> singIn(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if(user.isEmpty()){
            return Optional.empty();
        }
        return user;
    }

    @Override
    public Optional<User> signOut() {
        return Optional.empty();
    }

    @Override
    public Optional<User> describe() {
        return Optional.empty();
    }
}
