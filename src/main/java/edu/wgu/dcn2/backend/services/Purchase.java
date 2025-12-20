package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.entities.Cart;
import edu.wgu.dcn2.backend.entities.CartItem;
import edu.wgu.dcn2.backend.entities.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @NotNull
    @Valid
    private Customer customer;

    @NotNull
    @Valid
    private Cart cart;

    @NotEmpty
    private Set<CartItem> cartItems;
}
