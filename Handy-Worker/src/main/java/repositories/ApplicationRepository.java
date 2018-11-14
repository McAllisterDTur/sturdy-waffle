package repositories;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.HandyWorker;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Integer> {



	// Ratio de las aplicaciones aceptadas
	@Query("SELECT ((SELECT COUNT(A) FROM APPLICATION A WHERE A.STATUS = 'ACCEPTED')/(SELECT COUNT(B) FROM APPLICATION B))*100")
	Collection<HandyWorker> findRatioApplicationAccepted();
}
