
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalRecord;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {

	@Query("select pr from PersonalRecord pr where pr.curricula.id = ?1")
	public PersonalRecord findByCurricula(int curriculaId);
}
