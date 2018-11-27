
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import utilities.AuthenticationUtility;
import domain.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository	catRepo;


	/**
	 * Creates a new category (Req 12.3)
	 * 
	 * @return a new empty category
	 */
	public Category create() {
		return new Category();
	}
	/**
	 * Checks administrator authority. Saves or updates a category (Req 12.3)
	 * 
	 * @param warranty
	 * @return the warranty saved in the database
	 */
	public Category save(final Category category) {
		//Admin authority
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
		return this.catRepo.save(category);
	}

	/**
	 * Checks administrator authority (Req 12.3)
	 * 
	 * @return Collection of all the categories
	 */
	public Collection<Category> findAll() {
		//Admin authority
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
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
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
		this.catRepo.delete(category);
	}
	/**
	 * Deletes the category whose id is passed as parameter checking administrator authority. (Req 12.3)
	 * 
	 * @param categoryId
	 */
	public void delete(final int categoryId) {
		//Admin authority
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
		this.catRepo.delete(categoryId);
	}
}
