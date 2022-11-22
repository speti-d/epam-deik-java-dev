package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService{

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @Override
    public void createScreening(String movieTitle, String roomName, LocalDateTime screeningTime) {
        Movie movie = movieService.getMovieByTitle(movieTitle);
        Room room = roomService.getRoomByName(roomName);
        Screening screeningToSave = new Screening(movie, room, screeningTime);
//        Optional<Screening> screenings = screeningRepository.findLastBetweenByRoom(room, screeningTime,
//                screeningToSave.getEndOfBreak());
//        if (screenings.isEmpty()) {
            screeningRepository.save(screeningToSave);
//        } else if (screenings.get().getEndOfScreening().isAfter(screeningToSave.getScreeningTime())) {
//            throw new IllegalArgumentException("There is an overlapping screening");
//        } else if (screenings.get().getEndOfBreak().isAfter(screeningToSave.getScreeningTime())) {
//            throw new IllegalArgumentException("This would start in the break period after another screening in this room");
//        }

    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime screeningTime) {
//        Optional<Screening> screeningToDelete =
//                screeningRepository.findByMovieTitleAndRoomNameAndScreeningTime(movieTitle, roomName, screeningTime);
//        if (screeningToDelete.isEmpty()) {
//            throw new IllegalArgumentException("No such Screening exists.");
//        } else {
//            screeningRepository.delete(screeningToDelete.get());
//        }
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAll();
    }
}
