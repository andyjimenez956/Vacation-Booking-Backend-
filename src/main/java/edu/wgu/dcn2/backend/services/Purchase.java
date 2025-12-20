package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.entities.Cart;
import edu.wgu.dcn2.backend.entities.CartItem;
import edu.wgu.dcn2.backend.entities.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @NotNull
    private Customer customer;

    @NotNull
    private Cart cart;

    @NotNull
    @Size(min = 1, message = "cartItems must contain at least one item")
    private Set<CartItem> cartItems;
}
