
package repositories;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.HandyWorker;

@Repository
@Transactional
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	//QUERY
	//Ratio de fix-up con complaints
	@Query("SELECT ((SELECT COUNT(F) FROM COMPLAINT O JOIN O.FIXUPTASK F)/(SELECT COUNT(F) FROM FIXUPTASK F))*100")
	Collection<HandyWorker> findHandyWorkerMoreAverage();

	@Query("select c from Complaint c where c.ticker = ?1")
	public int getNumberOfTickers(String ticker);
}
