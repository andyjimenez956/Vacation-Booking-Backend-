package edu.wgu.dcn2.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    // --- DB column: customer_id ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    // Angular expects: id
    @JsonProperty("id")
    public Long getId() {
        return customerId;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.customerId = id;
    }


    // --- DB column: customer_first_name ---
    @NotBlank
    @Column(name = "customer_first_name")
    private String firstName;

    // --- DB column: customer_last_name ---
    @NotBlank
    @Column(name = "customer_last_name")
    private String lastName;

    // --- DB column: address ---
    @NotBlank
    @Column(name = "address")
    private String address;

    // --- DB column: postal_code ---
    @NotBlank
    @Column(name = "postal_code")
    @JsonProperty("postal_code")
    private String postalCode;

    // --- DB column: phone ---
    @NotBlank
    @Column(name = "phone")
    private String phone;

    /**
     * JSON helper ONLY (NOT a DB column).
     * Angular sends: division_id as a number.
     */
    @Transient
    @JsonProperty("division_id")
    private Long divisionId;

    // --- FK relationship: division_id ---
    @NotNull
    @ManyToOne
    @JoinColumn(name = "division_id")
    @JsonIgnore
    private Division division;

    /**
     * Expose division_id in JSON responses, and accept it in requests.
     */
    @JsonProperty("division_id")
    public Long getDivisionId() {
        return (division != null) ? division.getDivisionId() : divisionId;
    }

    @JsonProperty("division_id")
    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Cart> carts = new HashSet<>();

    public void add(Cart cart) {
        if (cart != null) {
            carts.add(cart);
            cart.setCustomer(this);
        }
    }
}
