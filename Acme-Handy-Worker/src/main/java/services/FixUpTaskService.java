
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

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;


	/**
	 * Checks customer authority
	 * 
	 * @param fixUpTask
	 * @return a new fix up task
	 */
	public FixUpTask create() {
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
		Assert.isTrue(fixUpTask.getCustomer().equals(userAccount));
		
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
		
		Assert.isTrue(res.getCustomer().getAccount().equals(userAccount));

		return res;

	}

	/**
	 * Deletes the fix up task whose id is passed as parameter checking customer authority.
	 * 
	 * @param fixUpTask
	 */
	public void delete(final int fixUpTaskId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		
		final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTaskId);
		
		Assert.isTrue(aux.getCustomer().getAccount().equals(userAccount));
		
		this.fixUpTaskRepository.delete(aux);
	}

	/**
	 * Deletes the fix up task passed as parameter checking customer authority.
	 * 
	 * @param fixUpTask
	 */
	public void delete(FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		
		final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTask.getId());
		
		Assert.isTrue(aux.getCustomer().getAccount().equals(userAccount));

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
	public Collection<FixUpTask> findAsHandyWorker(int handyWorkerId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		// Comprobamos que el usuario registrado sea un customer y propietario de la FixUpTask
		Authority au = new Authority();
		au.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findAsHandyWorker(handyWorkerId);
		return res;
	}

	public int getNumberOfTickers(final String ticker) {
		return this.fixUpTaskRepository.getNumberOfTickers(ticker);
	}
}
