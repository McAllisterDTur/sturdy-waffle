
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

	@Query("select er from EndorserRecord er where er.curricula.id = ?1")
	public EndorserRecord findByCurricula(int curriculaId);
}
