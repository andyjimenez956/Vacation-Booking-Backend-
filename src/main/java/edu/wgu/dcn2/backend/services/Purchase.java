package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.entities.Cart;
import edu.wgu.dcn2.backend.entities.CartItem;
import edu.wgu.dcn2.backend.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;

}
