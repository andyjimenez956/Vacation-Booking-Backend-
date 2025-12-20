package edu.wgu.dcn2.backend.dao;

import edu.wgu.dcn2.backend.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository extends JpaRepository<Division, Long> {
}
