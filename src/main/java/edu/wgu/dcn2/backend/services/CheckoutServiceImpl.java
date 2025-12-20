package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.dao.CustomerRepository;
import edu.wgu.dcn2.backend.entities.Cart;
import edu.wgu.dcn2.backend.entities.CartItem;
import edu.wgu.dcn2.backend.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Customer customer = purchase.getCustomer();
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        // generate tracking number
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // attach items to cart
        cartItems.forEach(cart::add);

        // attach cart to customer (this sets both sides)
        customer.add(cart);

        // save customer -> cascades cart + cartItems
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }
}
