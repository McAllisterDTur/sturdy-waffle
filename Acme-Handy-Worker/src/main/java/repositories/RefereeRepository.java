package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
@Transactional
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

}
