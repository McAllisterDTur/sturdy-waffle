
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

}
