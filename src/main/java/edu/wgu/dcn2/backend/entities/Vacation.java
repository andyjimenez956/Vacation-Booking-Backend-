package edu.wgu.dcn2.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    @JsonProperty("id") // optional: if frontend uses id
    private Long vacationId;

    @Column(name = "vacation_title")
    @JsonProperty("vacation_title")
    private String vacationTitle;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "travel_fare_price")
    @JsonProperty("travel_price")
    private BigDecimal travelFarePrice;

    @Column(name = "image_url")
    @JsonProperty("image_URL")
    private String imageUrl;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL)
    private Set<Excursion> excursions = new HashSet<>();
}
