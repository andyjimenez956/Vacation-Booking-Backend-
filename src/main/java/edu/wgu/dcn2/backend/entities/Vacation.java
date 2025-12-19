package edu.wgu.dcn2.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.smartcardio.CardTerminal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long vacationId;

    @Column(name = "vacation_title")
    private String vacationTitle;

    @Column(name = "travel_fare_price")
    private String travelFarePrice;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL)
    private Set<Excursion> excursions = new HashSet<>();
}
