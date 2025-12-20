package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.dao.CustomerRepository;
import edu.wgu.dcn2.backend.dao.DivisionRepository;
import edu.wgu.dcn2.backend.entities.Cart;
import edu.wgu.dcn2.backend.entities.CartItem;
import edu.wgu.dcn2.backend.entities.Customer;
import edu.wgu.dcn2.backend.entities.Division;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

        // ✅ Treat "id: 0" as NEW (otherwise Hibernate thinks it must UPDATE cart #0)
        if (cart.getCartId() != null && cart.getCartId() == 0L) {
            cart.setCartId(null);
        }

        // -----------------------------
        // 1) Use existing customer if ID provided
        // -----------------------------
        Customer customerToSave;

        if (incomingCustomer.getCustomerId() != null) {
            customerToSave = customerRepository.findById(incomingCustomer.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + incomingCustomer.getCustomerId()));

            // update fields (optional)
            customerToSave.setFirstName(incomingCustomer.getFirstName());
            customerToSave.setLastName(incomingCustomer.getLastName());
            customerToSave.setAddress(incomingCustomer.getAddress());
            customerToSave.setPostalCode(incomingCustomer.getPostalCode());
            customerToSave.setPhone(incomingCustomer.getPhone());

        } else {
            // New customer -> must map division_id
            customerToSave = incomingCustomer;

            if (customerToSave.getDivision() == null) {
                Long divId = customerToSave.getDivisionId();
                if (divId == null) {
                    throw new RuntimeException("division_id is required when creating a new customer");
                }
                Division division = divisionRepository.findById(divId)
                        .orElseThrow(() -> new RuntimeException("Division not found: " + divId));
                customerToSave.setDivision(division);
            }
        }

        // -----------------------------
        // 2) Tracking number + attach items
        // -----------------------------
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // Attach each cartItem properly
        cartItems.forEach(cart::add);

        // Attach cart to customer
        customerToSave.add(cart);

        // Save
        customerRepository.save(customerToSave);

        return new PurchaseResponse(orderTrackingNumber);
    }
}
