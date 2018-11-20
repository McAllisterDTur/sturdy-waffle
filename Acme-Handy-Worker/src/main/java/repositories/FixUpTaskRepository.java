
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f where f.customer.id = ?1")
	public Collection<FixUpTask> findFromCustomer(final int customerId);

	// TODO: Solo las que no han sido completadas o todas?
	@Query("select f from FixUpTask f")
	public Collection<FixUpTask> findAsHandyWorker(final int handyWorkerId);
}
