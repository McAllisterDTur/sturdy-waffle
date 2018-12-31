
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Endorsable;
import domain.HandyWorker;

@Repository
public interface EndorsableRepository extends JpaRepository<Endorsable, Integer> {

	@Query("select c from Application a join a.fixUpTask.customer c where a.status = 'ACCEPTED' and a.handyWorker.id = ?1")
	public Collection<Customer> findAllCustomerWhoWorkedWithHandy(int handyId);

	@Query("select a.handyWorker from Application a join a.fixUpTask.customer c where a.status = 'ACCEPTED' and c.id = ?1")
	public Collection<HandyWorker> findAllHandyWhoWorkedWithCusto(int custoId);

}
