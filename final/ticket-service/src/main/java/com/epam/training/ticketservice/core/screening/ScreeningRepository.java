package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
//    @Query("SELECT * FROM Screening scr WHERE scr.movieTitle = ?1 AND scr.roomName = ?2 AND scr.screeningTime = ?3")
//    Optional<Screening> findByMovieTitleAndRoomNameAndScreeningTime(String movieTitle, String roomName, LocalDateTime screeningTime);
//
//    @Query("SELECT * FROM Screening scr WHERE scr.roomName = ?1 AND scr.screeningTime BETWEEN ?2 AND ?3")
//    List<Screening> findAllBetweenByRoom(Room room, LocalDateTime screeningTime, LocalDateTime endOfBreak);
//
//    @Query("SELECT * FROM Screening scr WHERE scr.roomName = ?1 AND scr.screeningTime BETWEEN ?2 AND ?3 ORDER BY src.screeningTime DESC FETCH FIRST ROW ONLY")
//    Optional<Screening> findLastBetweenByRoom(Room room, LocalDateTime screeningTime, LocalDateTime endOfBreak);
}
