package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
@Transactional
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

}
