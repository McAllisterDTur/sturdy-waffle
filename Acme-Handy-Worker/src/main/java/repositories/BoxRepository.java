package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
@Transactional
public interface BoxRepository extends JpaRepository<Box, Integer> {

}
