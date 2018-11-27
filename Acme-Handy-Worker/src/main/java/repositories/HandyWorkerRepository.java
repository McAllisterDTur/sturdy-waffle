
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	//TODO
	// Queries

	// Lista de los handy-workers que tienen al menos un 10% m�s de aplicaciones
	// aceptadas que la media
	@Query("select avg(a.offeredPrice),  min(a.offeredPrice), max(a.offeredPrice), sqrt(sum(a.offeredPrice * a.offeredPrice) / count(a) - avg(a.offeredPrice) * avg(a.offeredPrice)) from Application a")
	Collection<HandyWorker> findHandyWorkerMoreAverage();

}
