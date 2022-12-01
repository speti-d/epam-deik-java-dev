package com.epam.training.ticketservice.core.screening;


import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import java.time.LocalDateTime;

@Entity
@Table(name = "Screenings")
@Data
@NoArgsConstructor
public class Screening {
    @Id
    @GeneratedValue
    private Long screeningId;
    @ManyToOne
    @JoinColumn(name = "movieTitle",
                    foreignKey = @ForeignKey(name = "movieTitleFK"))
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "roomName",
                    foreignKey = @ForeignKey(name = "roomNameFK"))
    private Room room;
    private LocalDateTime screeningTime;

    Screening(Movie movie, Room room, LocalDateTime screeningTime) {
        this.movie = movie;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    public LocalDateTime getEndOfScreening() {
        return screeningTime.plusMinutes(movie.getLengthInMinutes() + 1);
    }

    public LocalDateTime getEndOfBreak() {
        return this.getEndOfScreening().plusMinutes(10);
    }

}
