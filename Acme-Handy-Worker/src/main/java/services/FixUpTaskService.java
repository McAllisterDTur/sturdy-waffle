
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.LoginService;
import security.UserAccount;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	// Porque no podemos crear instancias de esta interfaz y spring hace su magia.
	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;
	private CustomerService		customerService;


	// Un actor que está autentificado como "Customer" debe poder hacer estas operaciones
	public FixUpTask create(final FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(fixUpTask.getCustomer().equals(userAccount));

		// Hay que recordar que la FixUpTask que devuelve el método .save del repositorio ha 
		// sido modificada al introducirse en la base de datos y permanece así en ella
		final FixUpTask res = this.fixUpTaskRepository.save(fixUpTask);
		return res;

	}

	public FixUpTask update(final FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(fixUpTask.getCustomer().equals(userAccount));

		// Hay que recordar que la FixUpTask que devuelve el método .save del repositorio ha 
		// sido modificada al introducirse en la base de datos y permanece así en ella
		final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTask.getId());
		fixUpTask.setId(aux.getId());
		final FixUpTask res = this.fixUpTaskRepository.save(fixUpTask);
		return res;

	}

	public Collection<FixUpTask> findFromCustomer(final int customerId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(this.customerService.findOne(customerId).equals(userAccount));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findFromCustomer(customerId);
		return res;
	}

	public FixUpTask findOne(final int fixUpTaskId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final FixUpTask res = this.fixUpTaskRepository.findOne(fixUpTaskId);

		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(res.getCustomer().equals(userAccount));

		return res;

	}

	public void delete(final FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(fixUpTask.getCustomer().equals(userAccount));

		this.fixUpTaskRepository.delete(fixUpTask);
	}
}
