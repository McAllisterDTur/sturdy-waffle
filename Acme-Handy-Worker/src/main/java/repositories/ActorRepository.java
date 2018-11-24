package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
@Transactional
public interface ActorRepository extends JpaRepository<Actor, Integer> {

}
