package edu.wgu.dcn2.backend.dao;

import edu.wgu.dcn2.backend.entities.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
