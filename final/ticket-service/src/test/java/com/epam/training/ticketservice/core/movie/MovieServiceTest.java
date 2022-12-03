package com.epam.training.ticketservice.core.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

public class MovieServiceTest {

    private final MovieRepository mockRepository = Mockito.mock(MovieRepository.class);

    private final MovieService underTest = new MovieServiceImpl(mockRepository);

    @Test
    void testCreateMovieShouldThrowExceptionWhenMovieAlreadyExists() {
        //Given
        Mockito.when(mockRepository.existsById(ArgumentMatchers.anyString())).thenReturn(true);

        //When & Then
        Assertions.assertThrows(IllegalArgumentException.class , ()
                -> underTest.createMovie("Amogus", "Horror", 99));
        Mockito.verify(mockRepository).existsById(ArgumentMatchers.anyString());
    }

    @Test
    void testCreateMovieShouldCreateMovieWhenPossible() {
        //Given
        Mockito.when(mockRepository.existsById(ArgumentMatchers.anyString())).thenReturn(false);

        //When
        underTest.createMovie("Amogus", "Horror", 99);

        //Then
        Mockito.verify(mockRepository).existsById(ArgumentMatchers.anyString());
        Mockito.verify(mockRepository).save(ArgumentMatchers.any(Movie.class));
    }

    @Test
    void testUpdateMovieShouldThrowExceptionWhenMovieDoesntExist() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        //When
        Assertions.assertThrows(IllegalArgumentException.class , ()
                -> underTest.updateMovie("amogus", "Horror", 100));
        //Then
        Mockito.verify(mockRepository).findById("amogus");
    }

    @Test
    void testUpdateMovieShouldUpdateTheMovieIfItExsits() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(new Movie()));

        //When
        underTest.updateMovie("amogus", "Horror", 100);

        //Then
        Mockito.verify(mockRepository).findById("amogus");
        Mockito.verify(mockRepository).save(ArgumentMatchers.any(Movie.class));
    }


    @Test
    void testDeleteMovieShouldThrowExceptionWhenMovieDoesntExist() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //When & Then
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> underTest.deleteMovie("Amogus"));
        Mockito.verify(mockRepository).findById(ArgumentMatchers.anyString());

    }

    @Test
    void testGetMovieByTitleShouldReturnMovieIfItExists() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(new Movie()));
        //When
        Assertions.assertInstanceOf(Movie.class, underTest.getMovieByTitle("sajt the movie"));
        //Then
        Mockito.verify(mockRepository).findById(ArgumentMatchers.anyString());
    }

    @Test
    void testGetMovieByTitleShouldThrowExceptionIfNoSuchMovieExists() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //When
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> {underTest.getMovieByTitle("sajt the movie");});
        //Then
        Mockito.verify(mockRepository).findById(ArgumentMatchers.anyString());
    }
}
