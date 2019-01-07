
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f where f.customer.id = ?1")
	public Collection<FixUpTask> findFromCustomer(final int customerId);

	@Query("select count(f) from FixUpTask f where f.ticker = ?1")
	public int getNumberOfTickers(String ticker);

	//@Query("select f from FixUpTask f where f.ticker like ?1 and f.description like ?1 and f.address like ?1 and f.category.name = ?2 and f.warranty.title = ?3 and f.maxPrice >= ?4 and f.maxPrice <= ?5 and (f.periodStart between ?6 and ?7)")
	@Query("select f from FixUpTask f where (f.ticker like %?1% or f.description like %?1% or f.address like %?1%) and f.category.name like %?2% and f.warranty.title like %?3% and f.maxPrice >= ?4 and f.maxPrice <= ?5 and f.periodStart between ?6 and ?7")
	public Collection<FixUpTask> findByFilter(String keyWord, String category, String warranty, double minPrice, double maxPrice, Date open, Date close);

	@Query("select f from FixUpTask f where f.category.id = ?1")
	public Collection<FixUpTask> getFixUpTasksByCategory(int catId);

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size) / count(c.fixUpTasks.size) - avg(c.fixUpTasks.size) * avg(c.fixUpTasks.size)) from Customer c")
	public List<Object[]> avgMinMaxDevFixUpTaskCount();

	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice), sqrt(sum(f.maxPrice * f.maxPrice) / count(f) - avg(f.maxPrice) * avg(f.maxPrice)) from FixUpTask f")
	public List<Object[]> avgMinMaxDevFixUpTaskPrice();

	@Query("select ((select count(distinct c.fixUpTask) from Complaint c) * 1.0 * 100 / (count(f))) from FixUpTask f")
	public Double ratioFixUpTaskComplaint();

	@Query("select f from FixUpTask f where (f.warranty.title like ?1 and f.description like ?2 and f.ticker like ?2 and f.address like ?2 and f.category.name like ?3 and f.maxPrice >= ?4 and f.maxPrice <= ?5 and f.periodStart >= ?6 and f.periodEnd <= ?7)")
	public List<FixUpTask> search(String warranty, String keyWord, String category, Double minPrice, Double maxPrice, Date startDate, Date endDate);

}
