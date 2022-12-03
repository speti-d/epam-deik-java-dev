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
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @Override
    public String createScreening(String movieTitle, String roomName, LocalDateTime screeningTime) {
        Movie movie = movieService.getMovieByTitle(movieTitle);
        Room room = roomService.getRoomByName(roomName);
        AtomicReference<String> result = new AtomicReference<>();
        Screening screeningToSave = new Screening(movie, room, screeningTime);
        Optional<List<Screening>> listOfScreenings = screeningRepository.findLastBetweenByRoom(
                room,
                screeningTime.minusMinutes(1000),
                screeningToSave.getEndOfScreening());
        if (listOfScreenings.isEmpty()) {
            screeningRepository.save(screeningToSave);
            result.set(String.format("Successfully created Screening: %s", screeningToSave));
        } else if (listOfScreenings.get().stream()
                .peek(existingScreening -> {
                    if (checkOverlap(screeningToSave, existingScreening)) {
                        result.set("There is an overlapping screening");
                    } else if (checkOverlapBreak(screeningToSave, existingScreening)) {
                        result.set("This would start in the break period after another screening in this room");
                    }
                })
                .anyMatch(existingScreening -> {
                    return checkOverlap(screeningToSave, existingScreening)
                        || checkOverlapBreak(screeningToSave, existingScreening);
                })) {
            return result.get();
        } else {
            screeningRepository.save(screeningToSave);
            result.set(String.format("Successfully created Screening: %s", screeningToSave));
        }
        return result.get();

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
