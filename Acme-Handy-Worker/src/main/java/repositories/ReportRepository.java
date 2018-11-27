
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	//TODO: cambiar query para la nueva relacion en UML
	//@Query("select r from Report where r.complaint.referee.id = ?1")
	//public Collection<Report> findAllReferee(final int refereeId);

}
