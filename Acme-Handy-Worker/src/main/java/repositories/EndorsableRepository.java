
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Endorsable;

@Repository
public interface EndorsableRepository extends JpaRepository<Endorsable, Integer> {

}
