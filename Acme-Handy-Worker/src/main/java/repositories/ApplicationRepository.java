
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.HandyWorker;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	// TODO: Esto debería estar en "HandyWorkerRepository"
	// Ratio de las aplicaciones aceptadas
	@Query("SELECT ((SELECT COUNT(A) FROM APPLICATION A WHERE A.STATUS = 'ACCEPTED')/(SELECT COUNT(B) FROM APPLICATION B))*100")
	Collection<HandyWorker> findRatioApplicationAccepted();

	// Listado de las "aplicaciones" 
	@Query("select a from Application a join (select f from FixUpTask where f.customer.id = ?1")
	public Collection<Application> findApplications(final int customerId);

}
