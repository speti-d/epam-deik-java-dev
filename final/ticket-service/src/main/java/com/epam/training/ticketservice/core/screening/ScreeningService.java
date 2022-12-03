package com.epam.training.ticketservice.core.screening;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    void createScreening(String movieTitle, String roomName, LocalDateTime screeningTime);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime screeningTime);

    List<Screening> listScreenings();
}
