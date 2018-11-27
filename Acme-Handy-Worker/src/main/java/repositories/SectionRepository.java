
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

	@Query("select s from Section s where s.tutorial.id = ?1")
	Collection<Section> findAllFromTutorial(int id);
}
