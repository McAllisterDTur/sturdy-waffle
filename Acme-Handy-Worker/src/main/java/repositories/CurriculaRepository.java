package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
@Transactional
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

}
