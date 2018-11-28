
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

	@Query("select er from Curricula c join c.endorserRecords er where c.id = ?1")
	public Collection<EndorserRecord> findByCurricula(int curriculaId);
}
