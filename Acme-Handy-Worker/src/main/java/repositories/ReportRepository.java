package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
@Transactional
public interface ReportRepository extends JpaRepository<Report, Integer> {

}
