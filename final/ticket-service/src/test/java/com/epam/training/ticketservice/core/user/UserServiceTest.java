package com.epam.training.ticketservice.core.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceTest {

    private final UserRepository mockRepository = Mockito.mock(UserRepository.class);

    private UserService underTest;


    @BeforeEach
    void initBeforeEach() {
        underTest = new UserServiceImpl(mockRepository);
    }

    @Test
    void testSignInShouldReturnOptionalUserWhenLoginCorrect() {
        //Given
        Optional<User> expected = Optional.of(new User("admin", "admin", User.Role.ADMIN));
        Mockito.when(mockRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(expected);
        //When
        Optional<User> result = underTest.singIn("admin", "admin");
        //Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSignInShouldReturnOptionalEmptyWhenLoginIncorrect() {
        //Given
        Optional<User> expected = Optional.empty();
        Mockito.when(mockRepository.findByUsernameAndPassword("cookie", "monster"))
                .thenReturn(Optional.empty());
        //When
        Optional<User> result = underTest.singIn("cookie", "monster");
        //Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testIsAdminShouldReturnTrueIfUserIsAnAdmin() {
        //Given
        Mockito.when(mockRepository.findByUsernameAndPassword("admin", "admin"))
                .thenReturn(Optional.of(new User("admin", "admin", User.Role.ADMIN)));
        underTest.singIn("admin", "admin");
        //When
        boolean result = underTest.isAdmin();
        //Then
        Assertions.assertTrue(result);
    }

    @Test
    void testIsAdminShouldReturnFalseIfUserIsNotSignedIn() {
        //Given & When
        boolean result = underTest.isAdmin();
        //Then
        Assertions.assertFalse(result);
    }

    @Test
    void testSignOutShouldSignOutTheCurrentUser() {
        //Given
        Mockito.when(mockRepository.findByUsernameAndPassword("admin", "admin"))
                .thenReturn(Optional.of(new User("admin", "admin", User.Role.ADMIN)));
        underTest.singIn("admin", "admin");
        //When
        underTest.signOut();
        //Then
        Assertions.assertEquals(Optional.empty(), underTest.describe());
    }
}
