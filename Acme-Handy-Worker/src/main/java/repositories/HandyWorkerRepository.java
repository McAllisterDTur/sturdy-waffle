
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	// Lista de los handy-workers que tienen al menos un 10% m�s de aplicaciones
	// aceptadas que la media
	@Query("select h from HandyWorker h where (select count(a) from Application a join a.handyWorker i where i.id = h.id) >= 1.1*((select count(b) from Application b)*1.0/(select count(j) from HandyWorker j)*1.0) order by (h.applications.size)")
	public Collection<HandyWorker> findHandyWorkerMoreAverage();

	//select top 3 distinct h from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints co order by co.size desc
	@Query("select h from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints co order by co.size desc")
	public Collection<HandyWorker> findAllHandyWorkerComplains();

}
