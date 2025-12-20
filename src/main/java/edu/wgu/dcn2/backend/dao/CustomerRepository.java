package edu.wgu.dcn2.backend.dao;

import edu.wgu.dcn2.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
