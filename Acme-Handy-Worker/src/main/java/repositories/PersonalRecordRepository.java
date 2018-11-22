
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PersonalRecord;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {

}
