package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
