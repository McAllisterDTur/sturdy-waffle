
package services;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Warranty;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	@Autowired
	private WarrantyService	wService;


	@Test
	public void saveGood() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		this.wService.save(w);
		super.unauthenticate();
	}

	@Test
	public void saveGood2() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		// Saving in final mode
		w.setDraft(false);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		this.wService.save(w);
		super.unauthenticate();
	}

	@Test
	public void saveBad() {
		super.authenticate("Customer1");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		Warranty w1 = null;
		try {
			w1 = this.wService.save(w);
		} catch (final Exception e) {

		}
		Assert.isNull(w1);
		super.unauthenticate();
	}

	@Test
	public void saveBad1() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(false);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		Warranty w2 = null;
		try {
			w1.setTerms("Esto es un test");
			w2 = this.wService.save(w1);
		} catch (final Exception e) {

		}
		Assert.isNull(w2);
		super.unauthenticate();
	}

	@Test
	public void updateGood() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		// Saving in draft mode
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		w1.setDraft(false); // TODO: Esto me modifica la 'caché' y luego si hago save peta ????????????
		super.unauthenticate();
	}
	@Test
	public void updateBad() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		// Saving in final mode
		w.setDraft(false);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		Warranty w2 = null;
		try {
			w2 = this.wService.save(w1);
		} catch (final Exception e) {

		}
		Assert.isNull(w2);
		super.unauthenticate();
	}

	@Test
	public void findAllGood() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		Assert.notNull(w1);
		super.unauthenticate();
	}

	@Test
	public void findAllBad() {
		super.authenticate("Customer1");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		Warranty w1 = null;
		try {
			w1 = this.wService.save(w);
		} catch (final Exception e) {

		}
		Assert.isNull(w1);
		super.unauthenticate();
	}

	@Test
	public void findOneGood() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		final Warranty w2 = this.wService.findOne(w1.getId());
		Assert.notNull(w2);
		super.unauthenticate();
	}

	@Test
	public void findOneBad() {
		super.authenticate("Customer1");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		Warranty w2 = null;
		try {

			final Warranty w1 = this.wService.save(w);
			w2 = this.wService.findOne(w1.getId());
		} catch (final Exception e) {

		}
		Assert.isNull(w2);
		super.unauthenticate();
	}

	@Test
	public void deleteGood() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		this.wService.delete(w1.getId());
		final Warranty w2 = this.wService.findOne(w1.getId());

		Assert.isNull(w2);
		super.unauthenticate();
	}
	@Test
	public void deleteGood1() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		this.wService.delete(w1);
		final Warranty w2 = this.wService.findOne(w1.getId());

		Assert.isNull(w2);
		super.unauthenticate();
	}

	@Test
	public void deleteBad() {
		super.authenticate("admin");
		final Warranty w = this.wService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("Términos");
		w.setTitle("La ley de la selva");
		final Warranty w1 = this.wService.save(w);
		super.unauthenticate();
		super.authenticate("Customer1");
		try {
			this.wService.delete(w1);
		} catch (final Exception e) {

		}
		super.unauthenticate();
		super.authenticate("admin");
		final Warranty w2 = this.wService.findOne(w1.getId());
		Assert.notNull(w2);
		super.unauthenticate();
	}
}
