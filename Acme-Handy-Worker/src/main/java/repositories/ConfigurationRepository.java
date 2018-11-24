package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
@Transactional
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

}
