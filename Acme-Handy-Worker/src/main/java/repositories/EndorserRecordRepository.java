package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
@Transactional
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

}
