
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select count(c) from Curricula c where c.ticker = ?1")
	public int getNumberOfTickers(String ticker);

	@Query("select c from Curricula c where c.handyWorker.id = ?1")
	public Curricula getFromHandyWorker(int handyId);
}
