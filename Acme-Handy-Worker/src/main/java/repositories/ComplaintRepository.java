
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
}
