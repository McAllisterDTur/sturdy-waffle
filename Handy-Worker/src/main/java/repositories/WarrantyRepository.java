package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
@Transactional
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

}
