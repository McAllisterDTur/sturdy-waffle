
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	@Query("select p from Phase p where p.application.handyWorker.id = ?1")
	public Collection<Phase> findAllWorker(final int workerId);

	//public Phase update(final Phase phase);
	@Query("select p from Phase p where p.application.id = ?1")
	public Collection<Phase> findFromApplication(final int workerId);
}
