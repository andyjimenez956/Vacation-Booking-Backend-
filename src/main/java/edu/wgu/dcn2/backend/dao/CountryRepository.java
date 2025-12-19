package edu.wgu.dcn2.backend.dao;

import edu.wgu.dcn2.backend.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface CountryRepository extends JpaRepository<Country, Long> {
}
