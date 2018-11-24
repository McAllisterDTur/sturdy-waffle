package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SocialProfile;

@Repository
@Transactional
public interface SocialProfileRepository extends JpaRepository<SocialProfile, Integer> {

}
