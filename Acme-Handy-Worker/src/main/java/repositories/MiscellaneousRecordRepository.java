
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

	@Query("select mr from Curricula c join c.miscellaneousRecords mr where c.id = ?1")
	public Collection<MiscellaneousRecord> findByCurricula(int curriculaId);
}
