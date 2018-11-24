package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
@Transactional
public interface FinderRepository extends JpaRepository<Finder, Integer> {

}
