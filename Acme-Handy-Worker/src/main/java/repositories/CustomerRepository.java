
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select distinct c from Customer c join c.fixUpTasks x where (select count(f) from FixUpTask f join f.customer d where d.id = c.id) >= 1.1*((select count(g) from FixUpTask g)*1.0/(select count(e) from Customer e )) order by x.applications.size desc")
	public Collection<Customer> findCustomerMaxAverage();

}
