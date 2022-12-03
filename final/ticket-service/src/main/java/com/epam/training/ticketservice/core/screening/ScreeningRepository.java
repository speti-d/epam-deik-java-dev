package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query("SELECT scr FROM Screening scr WHERE scr.movie = ?1 AND scr.room = ?2 AND scr.screeningTime = ?3")
    Optional<Screening> findByMovieTitleAndRoomNameAndScreeningTime(Movie movie,
                                                                    Room room,
                                                                    LocalDateTime screeningTime);

    @Query("SELECT scr FROM Screening scr WHERE scr.room = ?1 AND "
            + "scr.screeningTime BETWEEN ?2 AND ?3 ORDER BY scr.screeningTime DESC")
    Optional<List<Screening>> findLastBetweenByRoom(Room room, LocalDateTime screeningTime, LocalDateTime endOfBreak);
}
