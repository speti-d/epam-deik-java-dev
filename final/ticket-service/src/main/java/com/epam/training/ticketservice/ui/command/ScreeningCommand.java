package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommand {
    private final ScreeningService screeningService;
    private final CommandUtils commandUtils;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @ShellMethod(key = "create screening")
    @ShellMethodAvailability("isAdmin")
    public void createScreening(String movieTitle, String roomName, String screeningTime) {
        screeningService.createScreening(movieTitle, roomName, makeLocalDateTime(screeningTime));
    }

    @ShellMethod(key = "delete screening")
    @ShellMethodAvailability("isAdmin")
    public void deleteScreening(String movieTitle, String roomName, String screeningTime) {
        screeningService.deleteScreening(movieTitle, roomName, makeLocalDateTime(screeningTime));
    }

    @ShellMethod(key = "list screenings")
    public String listScreenings() {
        List<Screening> screeningList = screeningService.listScreenings();
        if (screeningList.isEmpty()) {
            return "There are no screenings";
        }
        return screeningList.stream()
                .map(screening -> String.format("%s (%s, %s minutes), screened in room %s, at %s",
                        screening.getMovie().getTitle(),
                        screening.getMovie().getGenre(),
                        screening.getMovie().getLengthInMinutes(),
                        screening.getRoom().getRoomName(),
                        screening.getScreeningTime().format(dateTimeFormatter)))
                .collect(Collectors.joining("\n"));
    }

    public LocalDateTime makeLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

    public Availability isAdmin() {
        return commandUtils.isAdmin();
    }
}
