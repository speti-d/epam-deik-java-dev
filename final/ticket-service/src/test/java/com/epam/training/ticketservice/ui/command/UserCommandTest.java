package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

public class UserCommandTest {
    private final UserService mockService = Mockito.mock(UserService.class);
    private final UserCommand underTest = new UserCommand(mockService);

    @Test
    void testSignInShouldFailWithWrongPass() {
        //Given
        String expected = "Login failed due to incorrect credentials";
        Mockito.when(mockService.singIn(ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //When
        String result = underTest.signInPrivileged("cookie", "monster");
        //Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSignInShouldSucceedWithCorrectPass() {
        //Given
        String expected = "Logged in as admin.";
        Mockito.when(mockService.singIn(ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(new User("admin", "admin", User.Role.ADMIN)));
        //When
        String result = underTest.signInPrivileged("admin", "admin");
        //Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testDescribeAccountIfNotSignedIn() {
        //Given
        String expected = "You are not signed in";
        Mockito.when(mockService.describe())
                .thenReturn(Optional.empty());
        //When
        String result = underTest.describeAccount();
        //Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testDescribeAccountIfSignedIn() {
        //Given
        String expected = "Signed in with privileged account 'admin'";
        Mockito.when(mockService.describe())
                .thenReturn(Optional.of(new User("admin", "admin", User.Role.ADMIN)));
        //When
        String result = underTest.describeAccount();
        //Then
        Assertions.assertEquals(expected, result);
    }
}
