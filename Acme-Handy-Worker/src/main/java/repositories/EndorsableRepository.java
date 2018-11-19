package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Endorsable;

@Repository
@Transactional
public interface EndorsableRepository extends JpaRepository<Endorsable, Integer> {

}
