
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.complaint.referee.id = ?1")
	public Collection<Report> findAllReportsReferee(final int refereeId);

	@Query("select r from Report r where r.complaint.fixUpTask.customer.id = ?1")
	public Collection<Report> findAllReportsCustomer(final int customerId);

	@Query("select r from Report r join r.complaint.fixUpTask.applications a where a.handyWorker.id = ?1 and a.status = 'ACCEPTED'")
	public Collection<Report> findAllReportsWorker(final int workerId);

}
