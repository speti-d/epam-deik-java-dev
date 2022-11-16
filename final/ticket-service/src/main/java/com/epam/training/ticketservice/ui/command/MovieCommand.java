package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {

    private final MovieService movieService;
    private final CommandUtils commandUtils;

    @ShellMethod(key = "create movie", value = "Create a new Movie")
    @ShellMethodAvailability("isAdmin")
    public void createMovie(String title, String genre, Integer lengthInMinutes) {
        movieService.createMovie(title, genre, lengthInMinutes);
    }

    @ShellMethod(key = "update movie", value = "Update an existing Movie")
    @ShellMethodAvailability("isAdmin")
    public void updateMovie(String title, String genre, Integer lengthInMinutes) {
        movieService.updateMovie(title, genre, lengthInMinutes);
    }

    @ShellMethod(key = "delete movie", value = "Delete a Movie")
    @ShellMethodAvailability("isAdmin")
    public void deleteMovie(String title) {
        movieService.deleteMovie(title);
    }

    @ShellMethod(key = "list movies", value = "List all Movies with details")
    public String listMovies() {
        List<Movie> movieList = movieService.listMovies();
        if (movieList.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movieList.stream()
                .map(movie -> String.format("%s (%s, %d minutes)",
                        movie.getTitle(), movie.getGenre(), movie.getLengthInMinutes()))
                .collect(Collectors.joining("\n"));
    }

    public Availability isAdmin() {
        return commandUtils.isAdmin();
    }

}
