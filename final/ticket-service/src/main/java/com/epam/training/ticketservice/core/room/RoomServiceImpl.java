package com.epam.training.ticketservice.core.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(String roomName, Integer rowCount, Integer colCount) {
        if (roomRepository.existsById(roomName)) {
            throw new IllegalArgumentException("Room with this name already exists.");
        } else {
            checkValidRoom(rowCount, colCount);
            roomRepository.save(new Room(roomName, rowCount, colCount));
        }
    }

    @Override
    public void updateRoom(String roomName, Integer rowCount, Integer colCount) {
        Optional<Room> roomToUpdate = roomRepository.findById(roomName);
        if (roomToUpdate.isEmpty()) {
            throw new IllegalArgumentException("No such Room exists.");
        } else {
            checkValidRoom(rowCount, colCount);
            Room updatedRoom = roomToUpdate.get();
            updatedRoom.setRowCount(rowCount);
            updatedRoom.setColCount(colCount);
            roomRepository.save(updatedRoom);
        }
    }

    @Override
    public void deleteRoom(String roomName) {
        Optional<Room> roomToDelete = roomRepository.findById(roomName);
        if (roomToDelete.isEmpty()) {
            throw new IllegalArgumentException("No such Room exists.");
        } else {
            roomRepository.delete(roomToDelete.get());
        }
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomByName(String roomName) {
        Optional<Room> roomToGet = roomRepository.findById(roomName);
        if (roomToGet.isEmpty()) {
            throw new IllegalArgumentException("No such Room exists");
        }
        return roomToGet.get();
    }

    /**
     * Checks if a room with the given parameters would be reasonable.
     * Throws IllegalArgumentException if that is not the case.
     */
    private void checkValidRoom(Integer row, Integer col) {
        if (!(row > 0 && col > 0)) {
            throw new IllegalArgumentException("You can't create a room with these parameters.");
        }
    }
}
