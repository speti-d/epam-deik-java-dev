package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @Override
    public void createScreening(String movieTitle, String roomName, LocalDateTime screeningTime) {
        Movie movie = movieService.getMovieByTitle(movieTitle);
        Room room = roomService.getRoomByName(roomName);
        Screening screeningToSave = new Screening(movie, room, screeningTime);
        Optional<List<Screening>> listOfScreenings = screeningRepository.findLastBetweenByRoom(
                room,
                screeningTime.minusMinutes(1000),
                screeningToSave.getEndOfScreening());
        if (listOfScreenings.isEmpty()) {
            screeningRepository.save(screeningToSave);
        } else {
            for (Screening existingScreening:
                 listOfScreenings.get()) {
                if (checkOverlap(screeningToSave, existingScreening)) {
                    throw new IllegalArgumentException("There is an overlapping screening");
                } else if (checkOverlapBreak(screeningToSave, existingScreening)) {
                    throw new IllegalArgumentException(
                            "This would start in the break period after another screening in this room");
                }
            }
            screeningRepository.save(screeningToSave);
        }
    }

    public boolean checkOverlap(Screening newScreening, Screening existingScreening) {
        return (newScreening.getScreeningTime().isBefore(existingScreening.getEndOfScreening())
                && newScreening.getScreeningTime().isAfter(existingScreening.getScreeningTime()));
    }

    public boolean checkOverlapBreak(Screening newScreening, Screening existingScreening) {
        return (newScreening.getScreeningTime().isBefore(existingScreening.getEndOfBreak())
                && newScreening.getScreeningTime().isAfter(existingScreening.getEndOfScreening().minusMinutes(1)));
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime screeningTime) {
        Movie movie = movieService.getMovieByTitle(movieTitle);
        Room room = roomService.getRoomByName(roomName);
        Optional<Screening> screeningToDelete =
                screeningRepository.findByMovieTitleAndRoomNameAndScreeningTime(movie, room, screeningTime);
        if (screeningToDelete.isEmpty()) {
            throw new IllegalArgumentException("No such Screening exists.");
        } else {
            screeningRepository.delete(screeningToDelete.get());
        }
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAll();
    }
}
