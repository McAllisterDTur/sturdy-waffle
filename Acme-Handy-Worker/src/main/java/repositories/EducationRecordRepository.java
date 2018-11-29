
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

	@Query("select er from Curricula c join c.educationRecord er where c.id = ?1")
	public Collection<EducationRecord> findByCurricula(int curriculaId);
}
