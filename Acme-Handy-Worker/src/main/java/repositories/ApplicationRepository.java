
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.fixUpTask.customer.id = ?1")
	public Collection<Application> findAllCustomer(int customerId);

	@Query("select a from Application a where a.handyWorker.id = ?1")
	public Collection<Application> findAllWorker(int workerId);

	//average, min, max and standard deviation
	@Query("select avg(a.offeredPrice), min(a.offeredPrice), max(a.offeredPrice),sqrt(sum(a.offeredPrice * a.offeredPrice) / count(a) - avg(a.offeredPrice) *avg(a.offeredPrice)) from Application a")
	public Collection<Double> statictisApplication();

	//ratio of pending
	@Query("select (select count(a) from Application a where a.status = 'PENDING') * 1.0 *100 / count(b) from Application b")
	public double ratioPendingApplications();

	//ratio of accepted
	@Query("select (select count(a) from Application a where a.status = 'ACCEPTED') * 1.0 *100 / count(b) from Application b")
	public double ratioAcceptedApplications();

	//ratio of rejected
	@Query("select (select count(a) from Application a where a.status = 'REJECTED') * 1.0 *100 / count(b) from Application b")
	public double ratioRejectedApplications();

	//ratio of pending whose period is elapsed
	@Query("select (select count(a) from Application a where a.status = 'PENDING' and a.fixUpTask.periodStart <= CURRENT_DATE) * 1.0 * 100 / count(b) from Application b")
	public double ratioElapsedApplications();

}
