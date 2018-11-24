package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
@Transactional
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

}
