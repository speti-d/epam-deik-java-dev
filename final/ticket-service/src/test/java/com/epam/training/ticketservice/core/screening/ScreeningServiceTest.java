package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private MovieService movieService;
    @Mock
    private RoomService roomService;
    @InjectMocks
    private ScreeningServiceImpl underTest;

    @Test
    void testCreateScreeningShouldCreateScreeningIfThereAreNoScreenings() {
        //Given
        LocalDateTime filmVetitese = LocalDateTime.of(2020, 3, 30, 12, 15);
        Mockito.when(movieService.getMovieByTitle(ArgumentMatchers.anyString()))
                .thenReturn(new Movie("Sajt the movie", "horror", 50));
        Mockito.when(roomService.getRoomByName(ArgumentMatchers.anyString()))
                .thenReturn(new Room("szoba", 5, 5));
        Mockito.when(screeningRepository.findLastBetweenByRoom(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any()))
                .thenReturn(Optional.of(List.of()));
        //When
        underTest.createScreening("Sajt the movie", "szoba", filmVetitese);
        //Then
        Mockito.verify(screeningRepository).findLastBetweenByRoom(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any());
        Mockito.verify(screeningRepository).save(ArgumentMatchers.any());
        Mockito.verify(movieService).getMovieByTitle(ArgumentMatchers.anyString());
        Mockito.verify(roomService).getRoomByName(ArgumentMatchers.anyString());
    }

    @Test
    void testCreateScreeningShouldCreateScreeningIfThereAreNoOverlaps() {
        //Given
        LocalDateTime filmVetitese = LocalDateTime.of(2020, 3, 30, 13, 1);
        Screening existingScreening = new Screening(
                new Movie("a","g",15),
                new Room("szoba", 5, 5),
                LocalDateTime.of(2020, 3, 30, 12, 0));
        Mockito.when(movieService.getMovieByTitle(ArgumentMatchers.anyString()))
                .thenReturn(new Movie("Sajt the movie", "horror", 50));
        Mockito.when(roomService.getRoomByName(ArgumentMatchers.anyString()))
                .thenReturn(new Room("szoba", 5, 5));
        Mockito.when(screeningRepository.findLastBetweenByRoom(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any()))
                .thenReturn(Optional.of(List.of(existingScreening)));
        //When
        underTest.createScreening("Sajt the movie", "szoba", filmVetitese);
        //Then
        Mockito.verify(screeningRepository).findLastBetweenByRoom(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any());
        Mockito.verify(screeningRepository).save(ArgumentMatchers.any());
        Mockito.verify(movieService).getMovieByTitle(ArgumentMatchers.anyString());
        Mockito.verify(roomService).getRoomByName(ArgumentMatchers.anyString());
    }

    @Test
    void testCreateScreeningShouldFailIfThereIsOverlap() {
        //Given
        LocalDateTime filmVetitese = LocalDateTime.of(2020, 3, 30, 12, 1);
        Screening existingScreening = new Screening(
                new Movie("a","g",15),
                new Room("szoba", 5, 5),
                LocalDateTime.of(2020, 3, 30, 12, 0));
        Mockito.when(movieService.getMovieByTitle(ArgumentMatchers.anyString()))
                .thenReturn(new Movie("Sajt the movie", "horror", 50));
        Mockito.when(roomService.getRoomByName(ArgumentMatchers.anyString()))
                .thenReturn(new Room("szoba", 5, 5));
        Mockito.when(screeningRepository.findLastBetweenByRoom(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any()))
                .thenReturn(Optional.of(List.of(existingScreening)));
        //When
        Assertions.assertThrows(IllegalArgumentException.class ,() -> {
            underTest.createScreening("Sajt the movie", "szoba", filmVetitese);
        } );
        //Then
        Mockito.verify(screeningRepository).findLastBetweenByRoom(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any());
        Mockito.verify(movieService).getMovieByTitle(ArgumentMatchers.anyString());
        Mockito.verify(roomService).getRoomByName(ArgumentMatchers.anyString());
    }

    @Test
    void testCreateScreeningShouldFailIfThereIsOverlapWithBreak() {
        //Given
        LocalDateTime filmVetitese = LocalDateTime.of(2020, 3, 30, 12, 11);
        Screening existingScreening = new Screening(
                new Movie("a","g",10),
                new Room("szoba", 5, 5),
                LocalDateTime.of(2020, 3, 30, 12, 0));
        Mockito.when(movieService.getMovieByTitle(ArgumentMatchers.anyString()))
                .thenReturn(new Movie("Sajt the movie", "horror", 50));
        Mockito.when(roomService.getRoomByName(ArgumentMatchers.anyString()))
                .thenReturn(new Room("szoba", 5, 5));
        Mockito.when(screeningRepository.findLastBetweenByRoom(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any()))
                .thenReturn(Optional.of(List.of(existingScreening)));
        //When
        Assertions.assertThrows(IllegalArgumentException.class ,() -> {
            underTest.createScreening("Sajt the movie", "szoba", filmVetitese);
        } );
        //Then
        Mockito.verify(screeningRepository).findLastBetweenByRoom(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any());
        Mockito.verify(movieService).getMovieByTitle(ArgumentMatchers.anyString());
        Mockito.verify(roomService).getRoomByName(ArgumentMatchers.anyString());
    }

    @ParameterizedTest
    @MethodSource("provideScreeningsForCheckOverlap")
    void testCheckOverlap(Screening newScreening, Screening existingScreening, Boolean expected) {
        //Given & When
        Boolean actual = underTest.checkOverlap(newScreening, existingScreening);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideScreeningsForCheckOverlapBreak")
    void testCheckOverlapBreak(Screening newScreening, Screening existingScreening, Boolean expected) {
        //Given & When
        Boolean actual = underTest.checkOverlapBreak(newScreening, existingScreening);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideScreeningsForCheckOverlap() {
        Movie testMovie = new Movie("TestTitle", "TestGenre", 10);
        Room testRoom = new Room("TestRoom", 5, 5);
        return Stream.of(
            Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:01")),
                    new Screening(testMovie,testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.TRUE),
            Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:11")),
                    new Screening(testMovie,testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.FALSE)
        );
    }
    private static Stream<Arguments> provideScreeningsForCheckOverlapBreak() {
        Movie testMovie = new Movie("TestTitle", "TestGenre", 10);
        Room testRoom = new Room("TestRoom", 5, 5);
        return Stream.of(
                Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:21")),
                        new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.FALSE),
                Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:20")),
                        new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.TRUE),
                Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:11")),
                        new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.TRUE),
                Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:19")),
                        new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.TRUE),
                Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:10")),
                        new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.FALSE),
                Arguments.of(new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:21")),
                        new Screening(testMovie, testRoom, LocalDateTime.parse("2022-03-30T12:00")), Boolean.FALSE)
        );
    }
}
