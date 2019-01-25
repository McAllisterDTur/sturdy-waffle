
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Box b join b.owner o where o.id = ?1")
	public Collection<Box> boxesByActor(int actorid);

	@Query("select b from Box b where b.owner.id = ?1 and b.name = ?2")
	public Collection<Box> boxByName(int actorid, String name);
}
