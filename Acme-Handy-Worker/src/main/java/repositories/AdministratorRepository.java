package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
@Transactional
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

}
