package com.epam.training.ticketservice.core.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteMovie(String title) {
        Optional<Movie> movieToDelete = movieRepository.findById(title);
        if (movieToDelete.isEmpty()) {
            throw new IllegalArgumentException("No such Movie exists.");
        } else {
            movieRepository.delete(movieToDelete.get());
        }
    }

    @Override
    public void updateMovie(String title, String genre, Integer lengthInMinutes) {
        Optional<Movie> movieToUpdate = movieRepository.findById(title);
        if (movieToUpdate.isEmpty()) {
            throw new IllegalArgumentException("No such Movie exists.");
        } else {
            Movie updatedMovie = movieToUpdate.get();
            updatedMovie.setGenre(genre);
            updatedMovie.setLengthInMinutes(lengthInMinutes);
            movieRepository.save(updatedMovie);
        }
    }

    @Override
    public void createMovie(String title, String genre, Integer lengthInMinutes) {
        if (movieRepository.existsById(title)) {
            throw new IllegalArgumentException("Movie with this title already exists.");
        } else {
            movieRepository.save(new Movie(title, genre, lengthInMinutes));
        }
    }

    @Override
    public Movie getMovieByTitle(String movieTitle) {
        Optional<Movie> movieToGet = movieRepository.findById(movieTitle);
        if (movieToGet.isEmpty()) {
            throw new IllegalArgumentException("No such Movie exists");
        }
        return movieToGet.get();
    }
}
