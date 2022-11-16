package com.epam.training.ticketservice.core.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(String roomName, Integer rowCount, Integer colCount) {
        if (roomRepository.existsById(roomName)) {
            throw new IllegalArgumentException("Room with this name already exists.");
        } else {
            roomRepository.save(new Room(roomName, rowCount, colCount));
        }
    }

    @Override
    public void updateRoom(String roomName, Integer rowCount, Integer colCount) {
        Optional<Room> roomToUpdate = roomRepository.findById(roomName);
        if (roomToUpdate.isEmpty()) {
            throw new IllegalArgumentException("No such Room exists.");
        } else {
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
}
