package com.epam.training.ticketservice.core.movie;

import java.util.List;

public interface MovieService {
    List<Movie> listMovies();

    void deleteMovie(String title);

    void updateMovie(String title, String genre, Integer lengthInMinutes);

    void createMovie(String title, String genre, Integer lengthInMinutes);
}
