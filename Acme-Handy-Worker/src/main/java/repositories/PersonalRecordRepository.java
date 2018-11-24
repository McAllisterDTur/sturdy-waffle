package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PersonalRecord;

@Repository
@Transactional
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {

}
