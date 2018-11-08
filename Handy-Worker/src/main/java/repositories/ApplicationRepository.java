package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
