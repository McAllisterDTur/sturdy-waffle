
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

}
