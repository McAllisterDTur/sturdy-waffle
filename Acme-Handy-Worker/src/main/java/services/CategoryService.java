
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import utilities.AuthenticationUtility;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	catRepo;
	@Autowired
	private FixUpTaskService	futService;


	/**
	 * Creates a new category (Req 12.3)
	 * 
	 * @return a new empty category
	 */
	public Category create() {
		final Category c = new Category();
		//c.setFather(this.findByName("CATEGORY"));
		return c;
	}
	/**
	 * Checks administrator authority. Saves or updates a category (Req 12.3)
	 * 
	 * @param category
	 * @return the category saved in the database
	 */
	public Category save(final Category category) {
		//Admin authority
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au, "You are not an administrator");
		Assert.notNull(category.getFather(), "This cannot be null");
		Assert.isTrue(category.getFather().getName() != "", "This cannot be null");
		return this.catRepo.save(category);
	}

	/**
	 * Checks administrator authority
	 * 
	 * @return Collection of all the categories
	 */
	public Category findByName(final String name) {
		//Admin authority
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
		Category c = this.catRepo.findByName(name);
		if (c == null)
			c = this.catRepo.findByNameEn(name);
		return c;
	}

	/**
	 * Checks administrator authority (Req 12.3)
	 * 
	 * @return Collection of all the categories
	 */
	public Collection<Category> findAll() {
		//Admin authority
		return this.catRepo.findAll();
	}
	/**
	 * Checks administrator authority. (Req 12.3)
	 * 
	 * @param categoryId
	 * @return the category whose id is the one passed as parameter
	 */
	public Category findOne(final int categoryId) {
		//Admin authority
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);

		return this.catRepo.findOne(categoryId);
	}

	/**
	 * Deletes the category passed as parameter checking administrator authority. (Req 12.3)
	 * 
	 * @param category
	 */
	public void delete(final Category category) {
		//Admin authority
		Assert.isTrue(!category.equals(this.findByName("CATEGORY")), "You cannot delete the category CATEGROY");
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
		final Collection<FixUpTask> inCat = this.futService.getByCategory(category.getId());
		for (final FixUpTask f : inCat) {
			f.setCategory(null);
			this.futService.save(f);
		}
		for (final Category c : this.findAll()) {
			final Category father = c.getFather();
			if (father != null && father.equals(category)) {
				c.setFather(category.getFather());
				this.catRepo.save(c);
			}
		}

		this.catRepo.delete(category);
	}
	/**
	 * Deletes the category whose id is passed as parameter checking administrator authority. (Req 12.3)
	 * 
	 * @param categoryId
	 */
	public void delete(final int categoryId) {
		//Admin authority
		final Category c = this.catRepo.findOne(categoryId);
		this.delete(c);
	}

	/**
	 * Returns all the categories that depend of a given category
	 * 
	 * @param Category
	 */
	public Collection<Category> findByFather(final Category c) {
		return this.catRepo.findByFather(c);
	}
}
