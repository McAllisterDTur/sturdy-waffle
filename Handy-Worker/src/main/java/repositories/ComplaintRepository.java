package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
@Transactional
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

}
