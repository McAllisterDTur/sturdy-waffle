
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

}
