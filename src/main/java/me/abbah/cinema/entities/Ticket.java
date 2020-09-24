package me.abbah.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    private double price;

    @Column(unique = true, nullable = true)
    private int codePayement;

    private boolean reservee;

    @ManyToOne
    private Place place;

    @ManyToOne
    private Projection projection;
}
