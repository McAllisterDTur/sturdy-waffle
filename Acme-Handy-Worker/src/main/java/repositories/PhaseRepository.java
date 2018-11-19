package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
@Transactional
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

}
