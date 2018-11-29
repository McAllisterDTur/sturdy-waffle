
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService	catService;


	@Test
	public void saveGoodTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		this.catService.save(c);
		super.unauthenticate();
	}

	@Test
	public void saveBadTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		super.unauthenticate();
		Category c2 = null;
		try {
			c2 = this.catService.save(c);
		} catch (final Exception e) {
		}

		Assert.isNull(c2);
	}

	@Test
	public void updateGoodTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		final Category c1 = this.catService.save(c);
		c1.setName("Cocina");
		this.catService.save(c1);
		System.out.println("=======Just checking categories, shouldn't have one named 'Ornitología'=======");
		for (final Category cat : this.catService.findAll())
			System.out.println(cat.getName());
		super.unauthenticate();
	}

	@Test
	public void updateBadTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		final Category c1 = this.catService.save(c);
		c1.setName("Cocina");
		super.unauthenticate();
		Category c2 = null;
		try {
			c2 = this.catService.save(c1);
		} catch (final Exception e) {
		}
		Assert.isNull(c2);
	}

	@Test
	public void findAllGoodTest() {
		super.authenticate("admin");
		final Collection<Category> col = this.catService.findAll();
		Assert.notNull(col);
		super.unauthenticate();
	}

	@Test
	public void findAllBadTest() {
		super.authenticate("Customer1");
		Collection<Category> col = null;
		try {
			col = this.catService.findAll();
		} catch (final Exception e) {
		}
		Assert.isNull(col);
		super.unauthenticate();
	}

	@Test
	public void findOneGoodTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		final Category c1 = this.catService.save(c);
		final Category c2 = this.catService.findOne(c1.getId());
		Assert.notNull(c2);
		super.unauthenticate();
	}

	@Test
	public void findOneBadTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		final Category c1 = this.catService.save(c);
		super.unauthenticate();
		Category c2 = null;
		try {
			c2 = this.catService.findOne(c1.getId());
		} catch (final Exception e) {
		}
		Assert.isNull(c2);
	}

	@Test
	public void deleteGoodTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		final Category c1 = this.catService.save(c);
		this.catService.delete(c1);
		final Category c2 = this.catService.findOne(c1.getId());
		Assert.isNull(c2);
		super.unauthenticate();
	}

	@Test
	public void deleteBadTest() {
		super.authenticate("admin");
		final Category c = this.catService.create();
		c.setFather(this.catService.findAll().iterator().next());
		c.setName("Ornitología");
		final Category c1 = this.catService.save(c);
		super.unauthenticate();
		try {
			this.catService.delete(c1);
		} catch (final Exception e) {
		}

		super.authenticate("admin");
		final Category c2 = this.catService.findOne(c1.getId());
		Assert.notNull(c2);
		super.unauthenticate();
	}
}
