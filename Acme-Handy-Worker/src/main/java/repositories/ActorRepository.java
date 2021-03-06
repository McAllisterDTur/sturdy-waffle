
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.account.id = ?1")
	public Actor findByUserAccountId(int accountId);

	@Query("select a from Actor a where a.isSuspicious = true")
	public Collection<Actor> findSuspiciousActors();
}
