package edu.wgu.dcn2.backend.config;

import edu.wgu.dcn2.backend.dao.CustomerRepository;
import edu.wgu.dcn2.backend.dao.DivisionRepository;
import edu.wgu.dcn2.backend.entities.Customer;
import edu.wgu.dcn2.backend.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public CustomerDataLoader(CustomerRepository customerRepository,
                      DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) {

        // ✅ If Andy Jimenez exists, we already seeded the 5 sample customers
        if (customerRepository.existsByFirstNameAndLastName("Andy", "Jimenez")) {
            return;
        }

        // Helper to fetch divisions (uses your seeded division ids)
        Division texas = divisionRepository.findById(42L).orElse(null);
        Division newYork = divisionRepository.findById(31L).orElse(null);
        Division florida = divisionRepository.findById(9L).orElse(null);
        Division california = divisionRepository.findById(4L).orElse(null);
        Division illinois = divisionRepository.findById(12L).orElse(null);

        // If any division ids are missing, don't crash your app
        if (texas == null || newYork == null || florida == null || california == null || illinois == null) {
            return;
        }

        // ---- Customer #1
        Customer c1 = new Customer();
        c1.setFirstName("Andy");
        c1.setLastName("Jimenez");
        c1.setAddress("742 Ranch Rd");
        c1.setPostalCode("78500");
        c1.setPhone("(956)555-0101");
        c1.setDivision(texas);

        // ---- Customer #2
        Customer c2 = new Customer();
        c2.setFirstName("Brianna");
        c2.setLastName("Jimenez");
        c2.setAddress("55 Party Ln");
        c2.setPostalCode("78501");
        c2.setPhone("(956)555-0102");
        c2.setDivision(texas);

        // ---- Customer #3
        Customer c3 = new Customer();
        c3.setFirstName("Maria");
        c3.setLastName("Lopez");
        c3.setAddress("900 Palm Ave");
        c3.setPostalCode("33101");
        c3.setPhone("(305)555-0199");
        c3.setDivision(florida);

        // ---- Customer #4
        Customer c4 = new Customer();
        c4.setFirstName("Chris");
        c4.setLastName("Nguyen");
        c4.setAddress("808 Lake Shore Dr");
        c4.setPostalCode("60601");
        c4.setPhone("(312)555-0147");
        c4.setDivision(illinois);

        // ---- Customer #5
        Customer c5 = new Customer();
        c5.setFirstName("Samantha");
        c5.setLastName("Reed");
        c5.setAddress("500 Market St");
        c5.setPostalCode("94105");
        c5.setPhone("(415)555-0133");
        c5.setDivision(california);

        customerRepository.save(c1);
        customerRepository.save(c2);
        customerRepository.save(c3);
        customerRepository.save(c4);
        customerRepository.save(c5);
    }
}
