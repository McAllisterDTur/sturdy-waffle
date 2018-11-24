package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
@Transactional
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

}
