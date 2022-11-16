package com.epam.training.ticketservice.core.room;

import java.util.List;

public interface RoomService {

    void createRoom(String roomName, Integer rowCount, Integer colCount);

    void updateRoom(String roomName, Integer rowCount, Integer colCount);

    void deleteRoom(String roomName);

    List<Room> listRooms();
}
