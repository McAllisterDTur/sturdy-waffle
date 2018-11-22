
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
