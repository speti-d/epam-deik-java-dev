package com.epam.training.ticketservice.core.room;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

public class RoomServiceTest {
    private final RoomRepository mockRepository = Mockito.mock(RoomRepository.class);
    private RoomService undertest;

    @BeforeEach
    void initBeforeEach() {
        undertest = new RoomServiceImpl(mockRepository);
    }

    @Test
    void testCreateRoomShouldThrowExceptionWhenRoomAlreadyExists() {
        //Given
        Mockito.when(mockRepository.existsById(ArgumentMatchers.anyString())).thenReturn(true);

        //When & Then
        Assertions.assertThrows(IllegalArgumentException.class , ()
                -> undertest.createRoom("Room1", 5, 5));
    }

    @Test
    void testDeleteRoomShouldThrowExceptionWhenRoomDoesntExist() {
        //Given
        Mockito.when(mockRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //When & Then
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> undertest.deleteRoom("Ruum1"));
    }
}
