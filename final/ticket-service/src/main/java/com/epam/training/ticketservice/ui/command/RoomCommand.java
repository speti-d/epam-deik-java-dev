package com.epam.training.ticketservice.ui.command;


import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {
    private final RoomService roomService;
    private final CommandUtils commandUtils;

    @ShellMethod(key = "create room", value = "Create a new Room")
    @ShellMethodAvailability("isAdmin")
    public void createRoom(String roomName, Integer rowCount, Integer colCount) {
        roomService.createRoom(roomName, rowCount, colCount);
    }

    @ShellMethod(key = "update room", value = "Update an existing Room")
    @ShellMethodAvailability("isAdmin")
    public void updateRoom(String roomName, Integer rowCount, Integer colCount) {
        roomService.updateRoom(roomName, rowCount, colCount);
    }

    @ShellMethod(key = "delete room", value = "Delete a Room")
    @ShellMethodAvailability("isAdmin")
    public void deleteRoom(String roomName) {
        roomService.deleteRoom(roomName);
    }

    @ShellMethod(key = "list rooms", value = "List all Rooms with details")
    public String listRooms() {
        List<Room> roomList = roomService.listRooms();
        if (roomList.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            return roomList.stream()
                    .map(room -> String
                            .format("Room %s with %d seats, %d rows and %d columns",
                                    room.getRoomName(),
                                    room.getChairCount(),
                                    room.getRowCount(),
                                    room.getColCount()))
                    .collect(Collectors.joining("\n"));
        }
    }

    public Availability isAdmin() {
        return commandUtils.isAdmin();
    }

}
