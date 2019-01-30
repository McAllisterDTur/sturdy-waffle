
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e  where e.reciever.id = ?1 or e.sender.id = ?1")
	public Collection<Endorsement> findAllCustomer(final int customerId);

	@Query("select e from Endorsement e  where e.reciever.id = ?1 or e.sender.id = ?1")
	public Collection<Endorsement> findAllWorker(final int workerrId);

	@Query("select e from Endorsement e  where e.reciever.id = ?1 or e.sender.id = ?1")
	public Collection<Endorsement> findAllEndorsable(int endorsableId);

	@Query("select e from Endorsement e  where e.reciever.id = ?1")
	public Collection<Endorsement> findAllReceivedByEndorsable(int endorsableId);

	@Query("select e from Endorsement e  where e.sender.id = ?1")
	public Collection<Endorsement> findAllSentByEndorsable(int endorsableId);

	@Query("select c from Customer c join c.fixUpTasks f join f.applications a where a.handyWorker.id = ?1")
	public Collection<Customer> getCustomerFromTask(final int workerrId);

	@Query("select h from HandyWorker h join h.applications a where a.fixUpTask.customer.id = ?1")
	public Collection<HandyWorker> getWorkerFromTask(final int customerId);

}
