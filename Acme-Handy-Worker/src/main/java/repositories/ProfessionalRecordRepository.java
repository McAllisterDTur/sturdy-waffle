package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
@Transactional
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer> {

}
