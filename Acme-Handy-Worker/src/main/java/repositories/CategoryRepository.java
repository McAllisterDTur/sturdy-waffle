
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.name = ?1")
	public Category findByName(String name);

	@Query("select c from Category c where c.nameEn = ?1")
	public Category findByNameEn(String name);

	@Query("select c from Category c where c.father = ?1")
	public Collection<Category> findByFather(Category category);
}
