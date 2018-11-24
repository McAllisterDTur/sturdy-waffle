package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
@Transactional
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

}
