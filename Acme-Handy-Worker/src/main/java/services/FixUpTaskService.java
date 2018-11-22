
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
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
	 * Checks customer authority
	 * 
	 * @param fixUpTask
	 * @return a new fix up task
	 */
	public FixUpTask create() {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		final Authority au = new Authority();
		au.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		final FixUpTask res = new FixUpTask();
		return res;

	}

	/**
	 * Checks customer authority.
	 * 
	 * @param fixUpTask
	 * @return the fix up task saved in the database
	 */
	public FixUpTask save(final FixUpTask fixUpTask) {
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
	 * Checks customer authority.
	 * 
	 * @param fixUpTaskId
	 * @return the fix up task whose id is the one passed as parameter
	 */
	public FixUpTask findOne(final int fixUpTaskId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final FixUpTask res = this.fixUpTaskRepository.findOne(fixUpTaskId);

		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Assert.isTrue(res.getCustomer().getAccount().equals(userAccount));

		return res;

	}

	/**
	 * Deletes the fix up task passed as parameter checking customer authority.
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
	 * Deletes the fix up task whose id is the one passed as parameter checking customer authority.
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
	 * Checks customer authority
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
	 * Checks customer authority
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

	public int getNumberOfTickers(final String ticker) {
		return this.fixUpTaskRepository.getNumberOfTickers(ticker);
	}
}
