
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	@Query("SELECT A FROM APPLICATION JOIN A.FIXUPTASK.CUSTOMER.ID = ?1")
	public Collection<Application> findAllCustomer(final int customerId);

	@Query("SELECT A FROM APPLICATION JOIN A.FIXUPTASK.CUSTOMER.ID = ?1")
	public Collection<Application> findAllWorker(final int workerId);

}
