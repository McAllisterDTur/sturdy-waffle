
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


	// TODO: Implementar "CustomerService"
	// private CustomerService customerService;
	// TODO: Implementar "HandyWorkerService"
	// private HandyWorkerService handyWorkerService;

	/**
	 * This method is responsible for checking that the user is a customer.
	 * Creates the fix up task passed as parameter.
	 * 
	 * @param fixUpTask
	 */
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

	/**
	 * This method is responsible for checking that the user is a customer.
	 * Updates the fix up task passed as parameter.
	 * 
	 * @param fixUpTask
	 */
	// TODO: This method will fail since we dont have a way to track the original FixUpTask
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

	/**
	 * This method is responsible for checking that the user is a customer.
	 * Finds the fix up task whose id is the one passed as parameter.
	 * 
	 * @param fixUpTaskId
	 */
	public FixUpTask findOne(final int fixUpTaskId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final FixUpTask res = this.fixUpTaskRepository.findOne(fixUpTaskId);

		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(res.getCustomer().equals(userAccount));

		return res;

	}

	/**
	 * This method is responsible for checking that the user is a customer.
	 * Deletes the fix up task passed as parameter.
	 * 
	 * @param fixUpTask
	 */
	public void delete(final FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(fixUpTask.getCustomer().equals(userAccount));
		final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTask.getId());
		this.fixUpTaskRepository.delete(aux);
	}

	/**
	 * This method is responsible for checking that the user is a customer.
	 * Deletes the fix up task whose id is the one passed as parameter.
	 * 
	 * @param fixUpTaskId
	 */
	public void delete(final int fixUpTaskId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.isTrue(aux.getCustomer().equals(userAccount));

		this.fixUpTaskRepository.delete(aux);
	}

	/**
	 * This method is responsible for checking that the user is a customer
	 * 
	 * @param CustomerId
	 * @return Collection of the fix up tasks related to a customer
	 */
	public Collection<FixUpTask> findFromCustomer(final int customerId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		// TODO: Implementar el servicio "CustomerService"
		// Assert.isTrue(this.customerService.findOne(customerId).equals(userAccount));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findFromCustomer(customerId);
		return res;
	}

	/**
	 * This method is responsible for checking that the user is a handy worker
	 * 
	 * @param handyWorkerId
	 * @return Collection of all the fix up tasks
	 */
	public Collection<FixUpTask> findAsHandyWorker(final int handyWorkerId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		// TODO: Implementar el servicio "HandyWorkerService"
		// Assert.isTrue(this.handyWorkerService.findOne(handyWorkerId).equals(userAccount));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findAsHandyWorker(handyWorkerId);
		return res;
	}
}
