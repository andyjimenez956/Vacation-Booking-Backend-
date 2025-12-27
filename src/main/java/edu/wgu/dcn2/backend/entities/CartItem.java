package edu.wgu.dcn2.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public Long getId() { return cartItemId; }

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public void setId(Long id) { this.cartItemId = (id != null && id == 0) ? null : id; }

    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    // ✅ ADD THIS
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @ManyToMany
    @JoinTable(
            name = "excursion_cartitem",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "excursion_id")
    )
    private Set<Excursion> excursions = new HashSet<>();
}
