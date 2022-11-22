package com.epam.training.ticketservice.core.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @Column(unique = true)
    private String roomName;
    private Integer rowCount;
    private Integer colCount;

    public Integer getChairCount() {
        return rowCount * colCount;
    }

}
