
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f where f.customer.id = ?1")
	public Collection<FixUpTask> findFromCustomer(final int customerId);

	@Query("select f from FixUpTask f where f.ticker = ?1")
	public int getNumberOfTickers(String ticker);

	@Query("select f from FixUpTask f where (f.ticker like %?1% or f.description like %?1% or f.address like %?1%) and f.category.name = ?2 and f.warranty.title = ?3 and f.maxPrice >= ?4 and f.maxPrice <= ?5 and f.periodStart >= ?6 and f.periodStart <= ?7")
	public
		Collection<FixUpTask> findByFilter(String keyWord, String category, String warranty,
			double minPrice, double maxPrice, Date open, Date close);

}
