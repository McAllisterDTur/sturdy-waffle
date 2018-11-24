package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
@Transactional
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

}
