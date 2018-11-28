
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Finder f where f.worker.id = ?1")
	public Finder findByHandyWorker(int handyId);

	@Query("select f from FixUpTask f where (f.warranty.title like ?1 and f.description like ?2 and f.ticker like ?2 and f.address like ?2 and f.category.name like ?3 and f.maxPrice >= ?4 and f.maxPrice <= ?5 and f.periodStart >= ?6 and f.periodEnd <= ?7)")
	public List<FixUpTask> search(String warranty, String keyWord, String category, Double minPrice, Double maxPrice, Date startDate, Date endDate);

	//select f from FixUpTask f where (f.warranty.title like '%%' and f.description like '%%' and f.ticker like '%%' and f.address like '%%' and f.category.name like '%%' and f.maxPrice >= 0.0 and f.maxPrice <= 9000.0 and f.periodStart >= '01/01/1970' and f.periodEnd >= '12/04/2024')
}
