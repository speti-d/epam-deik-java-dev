package com.epam.training.ticketservice.core.room;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RoomTest {

    @Test
    void testGetChairCountShouldReturnProductOfRowsAndCols() {
        //Given
        Room room = new Room("name", 5, 5);
        //When & Then
        Assertions.assertEquals(25, room.getChairCount());
    }
}
