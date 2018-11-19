package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
@Transactional
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

}
