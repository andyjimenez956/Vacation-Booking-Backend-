package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.dao.CustomerRepository;
import edu.wgu.dcn2.backend.dao.DivisionRepository;
import edu.wgu.dcn2.backend.entities.*;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Customer incomingCustomer = purchase.getCustomer();
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        Customer customerToUse;

        if (incomingCustomer.getCustomerId() != null) {

            customerToUse = customerRepository.findById(incomingCustomer.getCustomerId())
                    .orElseThrow(() -> new RuntimeException(
                            "Customer not found: " + incomingCustomer.getCustomerId()));


            customerToUse.setFirstName(incomingCustomer.getFirstName());
            customerToUse.setLastName(incomingCustomer.getLastName());
            customerToUse.setAddress(incomingCustomer.getAddress());
            customerToUse.setPostalCode(incomingCustomer.getPostalCode());
            customerToUse.setPhone(incomingCustomer.getPhone());

        } else {

            Long divId = incomingCustomer.getDivisionId();
            if (divId == null) {
                throw new RuntimeException("division_id is required when creating a new customer");
            }

            Division division = divisionRepository.findById(divId)
                    .orElseThrow(() -> new RuntimeException("Division not found: " + divId));

            incomingCustomer.setDivision(division);

            // IMPORTANT: do NOT null/overwrite customerId
            customerToUse = incomingCustomer;
        }


        cart.setId(0L);

        cart.setCustomer(customerToUse);

        cart.setStatus(StatusType.ordered);

        String trackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(trackingNumber);

        LocalDateTime now = LocalDateTime.now();
        cart.setCreateDate(now);
        cart.setLastUpdate(now);

        for (CartItem item : cartItems) {
            item.setId(0L);
            item.setCart(cart);
            item.setCreateDate(now);
            item.setLastUpdate(now);

            cart.getCartItems().add(item);
        }


        Customer savedCustomer = customerRepository.save(customerToUse);

        cart.setCustomer(savedCustomer);


        savedCustomer.getCarts().add(cart);


        customerRepository.save(savedCustomer);

        return new PurchaseResponse(trackingNumber);
    }
}
