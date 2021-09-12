package com.example.mostvaluableplayer.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "handball")
public class HandballPlayer extends Player{
    private int goalsMade;
    private int goalsReceived;
}
