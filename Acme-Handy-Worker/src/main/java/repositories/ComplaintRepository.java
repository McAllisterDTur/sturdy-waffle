
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select count(c) from Complaint c where c.ticker = ?1")
	public int getNumberOfTickers(String ticker);

	@Query("select c from Complaint c join c.fixUpTask f where f.customer.id = ?1")
	public Collection<Complaint> findFromCustomer(int customerId);

	@Query("select c from Complaint c join c.fixUpTask f where f.customer.id = ?1 and c.isFinal = 1")
	public Collection<Complaint> findFinalFromCustomer(int customerId);

	@Query("select c from Complaint c join c.fixUpTask f where f.customer.id = ?1 and c.isFinal = 0")
	public Collection<Complaint> findDraftedFromCustomer(int customerId);

	@Query("select c from Complaint c join c.fixUpTask.applications a where a.handyWorker.id = ?1 and c.isFinal = 1")
	public Collection<Complaint> findFromHandyWorker(int id);

	@Query("select c from Complaint c where c.referee = null and c.isFinal = 1")
	public Collection<Complaint> findUnassigned();

	@Query("select c from Complaint c where c.referee.id = ?1 and c.isFinal = 1")
	public Collection<Complaint> findSelfassigned(int id);

	@Query("select max(f.complaints.size), min(f.complaints.size), avg(f.complaints.size), sqrt(sum(f.complaints.size * f.complaints.size) / count(f.complaints.size) - avg(f.complaints.size) * avg(f.complaints.size)) from FixUpTask f")
	public Collection<Double> minMaxAvgDevComplaintsPerFixUpTask();

	@Query("select c from Complaint c join c.fixUpTask f where f.id = ?1 and c.isFinal = 1")
	public Collection<Complaint> findFromFixUpTask(int id);
}
