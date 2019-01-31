
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select distinct c from Customer c where (select count(f) from FixUpTask f join f.customer q where q.id = c.id) >= 1.1*((select count(task) from FixUpTask task)*1.0/(select count(y) from Customer y)*1.0)) order by (select count(apps) from c.fixUpTasks.applications.size apps) desc")
	public Collection<Customer> findCustomerMaxAverage();

	//select top 3 distinct c from Customer c join c.fixUpTasks f join f.complaints co order by co.size desc
	@Query("select c from Customer c join c.fixUpTasks f join f.complaints co order by co.size desc")
	public Collection<Customer> findCustomerMaxComplaints();

}
