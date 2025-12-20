package edu.wgu.dcn2.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "excursions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_id")
    @JsonProperty("id")
    private Long excursionId;

    @Column(name = "excursion_title")
    @JsonProperty("excursion_title")
    private String excursionTitle;

    @Column(name = "excursion_price")
    @JsonProperty("excursion_price")
    private BigDecimal excursionPrice;

    @Column(name = "image_url")
    @JsonProperty("image_URL")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    // if you have cartitems relationship, keep it but ignore recursion if needed
}
