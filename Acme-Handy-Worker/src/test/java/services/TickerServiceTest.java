
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class TickerServiceTest {

	@Autowired
	private TickerService	tickerService;


	@Test
	public void getTickerGood() {
		final String t = this.tickerService.getTicker();
		System.out.println(t);
		Assert.isTrue(t != null);
	}

}
