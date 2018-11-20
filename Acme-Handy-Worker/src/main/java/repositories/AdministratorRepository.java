
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), " + "sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size) / count(c.fixUpTasks.size) - " + "avg(c.fixUpTasks.size) * avg(c.fixUpTasks.size)) from Customer c")
	Collection<Double> fixUpTaskPerUserStats();
}
