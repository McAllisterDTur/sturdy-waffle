
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer> {

	@Query("select pr from Curricula c join c.professionalRecords pr where c.id = ?1")
	public Collection<ProfessionalRecord> findByCurricula(int curriculaId);
}
