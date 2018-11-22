
package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
@Transactional
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {
	//
	//	// Queries
	//
	//	// Lista de los handy-workers que tienen al menos un 10% más de aplicaciones
	//	// aceptadas que la media
	//	@Query("SELECT W FROM HANDYWORKER W WHERE (SELECT COUNT(P) FROM W.APPLICATION P WHERE P.ID_HANDYWORKER = W.ID_HANDYWORKER AND P.STATUS = 'ACCEPTED') >= "
	//		+ "SELECT ((SELECT COUNT(A) FROM HANDYWORKER Y JOIN Y.APPLICATION A WHERE A.STATUS='ACCEPTED')/(SELECT COUNT(H) FROM HANDYWORKER H))")
	//	Collection<HandyWorker> findHandyWorkerMoreAverage();

}
