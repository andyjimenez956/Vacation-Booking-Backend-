package edu.wgu.dcn2.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    // Angular sends "id"
    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public Long getId() {
        return cartId;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public void setId(Long id) {
        this.cartId = (id != null && id == 0) ? null : id;
    }

    @NotNull
    @Column(name = "package_price")
    @JsonProperty("package_price")
    private Double packagePrice;

    @NotNull
    @Column(name = "party_size")
    @JsonProperty("party_size")
    private Integer partySize;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "create_date")
    private LocalDateTime createDate;
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.lastUpdate = now;
    }


    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
    public void add(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }
}
