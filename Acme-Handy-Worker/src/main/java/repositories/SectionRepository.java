package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Section;

@Repository
@Transactional
public interface SectionRepository extends JpaRepository<Section, Integer> {

}
