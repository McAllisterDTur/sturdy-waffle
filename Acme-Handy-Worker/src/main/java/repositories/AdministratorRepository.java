
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select max(f.complaints.size), min(f.complaints.size), avg(f.complaints.size),sqrt(sum(f.complaints.size * f.complaints.size) / count(f.complaints.size) -avg(f.complaints.size) * avg(f.complaints.size)) from FixUpTask f")
	public List<Object[]> complaintsPerFixUpTaskStatistics();

	@Query("select max(r.notes.size), min(r.notes.size), avg(r.notes.size),sqrt(sum(r.notes.size * r.notes.size) / count(r) - avg(r.notes.size) * avg(r.notes.size))from Report r")
	public List<Object[]> notesPerRefereeReportStatistics();

	@Query("select ((select count(distinct c.fixUpTask) from Complaint c) * 1.0 * 100 /(count(f))) from FixUpTask f")
	public Double fixUpTaskWithComplaintRatio();

	@Query("select distinct c from Customer c join c.fixUpTasks f join f.complaints co order by co.size desc")
	public List<Customer> topThreeCustomersByComplaints();

	@Query("select distinct h from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints co order by co.size desc")
	public List<HandyWorker> topThreeHandyByComplaints();
}
