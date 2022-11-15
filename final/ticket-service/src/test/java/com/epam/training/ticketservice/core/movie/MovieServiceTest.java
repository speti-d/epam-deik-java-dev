package com.epam.training.ticketservice.core.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

public class MovieServiceTest {

    private final MovieRepository mockRepository = Mockito.mock(MovieRepository.class);

    private MovieService underTest;

    @BeforeEach
    void initBeforeEach() {
        underTest = new MovieServiceImpl(mockRepository);
    }

    @Test
    void testCreateMovieShouldThrowExceptionWhenMovieAlreadyExists() {
        //Given
        Mockito.when(mockRepository.existsById(ArgumentMatchers.anyString())).thenReturn(true);

        //When & Then
        Assertions.assertThrows(IllegalArgumentException.class , ()
                -> underTest.createMovie("Amogus", "Horror", 99));
    }


    @Test
    void testDeleteMovieShouldThrowExceptionWhenMovieDoesntExist() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //When & Then
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> underTest.deleteMovie("Amogus"));

    }
}
