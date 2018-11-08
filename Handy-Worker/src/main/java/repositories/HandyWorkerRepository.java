package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
@Transactional
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

}
