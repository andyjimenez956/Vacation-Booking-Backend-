package edu.wgu.dcn2.backend.services;

import edu.wgu.dcn2.backend.dao.CustomerRepository;
import edu.wgu.dcn2.backend.dao.DivisionRepository;
import edu.wgu.dcn2.backend.entities.Cart;
import edu.wgu.dcn2.backend.entities.CartItem;
import edu.wgu.dcn2.backend.entities.Customer;
import edu.wgu.dcn2.backend.entities.Division;
import edu.wgu.dcn2.backend.entities.StatusType;
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

        // --- Pull payload pieces ---
        Customer incomingCustomer = purchase.getCustomer();
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        // ------------------------------------------------------------
        // 0) IMPORTANT: Angular often sends id: 0 for new entities.
        //    Force Hibernate to INSERT by setting IDs to null.
        // ------------------------------------------------------------
        cart.setCartId(null);
        for (CartItem item : cartItems) {
            item.setCartItemId(null);
        }

        // ------------------------------------------------------------
        // 1) Customer handling: use existing customer if id provided,
        //    otherwise create a new customer and resolve division_id.
        // ------------------------------------------------------------
        Customer customerToSave;

        if (incomingCustomer.getCustomerId() != null) {
            // Existing customer
            customerToSave = customerRepository.findById(incomingCustomer.getCustomerId())
                    .orElseThrow(() -> new RuntimeException(
                            "Customer not found: " + incomingCustomer.getCustomerId()));

            // Update basic fields (safe)
            customerToSave.setFirstName(incomingCustomer.getFirstName());
            customerToSave.setLastName(incomingCustomer.getLastName());
            customerToSave.setAddress(incomingCustomer.getAddress());
            customerToSave.setPostalCode(incomingCustomer.getPostalCode());
            customerToSave.setPhone(incomingCustomer.getPhone());

            // If you want to allow changing division, uncomment and use division_id:
            // Long divId = incomingCustomer.getDivisionId();
            // if (divId != null) {
            //     Division division = divisionRepository.findById(divId)
            //             .orElseThrow(() -> new RuntimeException("Division not found: " + divId));
            //     customerToSave.setDivision(division);
            // }

        } else {
            // New customer
            customerToSave = incomingCustomer;

            // Resolve division (Angular sends division_id as number)
            Long divId = incomingCustomer.getDivisionId();
            if (divId == null) {
                throw new RuntimeException("division_id is required when creating a new customer");
            }

            Division division = divisionRepository.findById(divId)
                    .orElseThrow(() -> new RuntimeException("Division not found: " + divId));

            customerToSave.setDivision(division);
        }

        // ------------------------------------------------------------
        // 2) Cart fields required by rubric + DB
        // ------------------------------------------------------------
        cart.setStatus(StatusType.ORDERED);

        // timestamps (so create_date / last_update are NOT NULL)
        LocalDateTime now = LocalDateTime.now();
        cart.setCreateDate(now);
        cart.setLastUpdate(now);

        for (CartItem item : cartItems) {
            item.setCreateDate(now);
            item.setLastUpdate(now);
        }

        // tracking number
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // ------------------------------------------------------------
        // 3) Link relationships (IMPORTANT)
        // ------------------------------------------------------------
        // Cart -> CartItems
        cartItems.forEach(cart::add);

        // Customer -> Cart (also sets cart.customer via your add() method)
        customerToSave.add(cart);

        // ------------------------------------------------------------
        // 4) Save (cascades persist cart + items through customer.add(cart))
        // ------------------------------------------------------------
        customerRepository.save(customerToSave);

        return new PurchaseResponse(orderTrackingNumber);
    }
}
