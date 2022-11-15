package com.epam.training.ticketservice.core.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @Column(unique = true)
    private String title;
    private String genre;
    private Integer lengthInMinutes;


}
