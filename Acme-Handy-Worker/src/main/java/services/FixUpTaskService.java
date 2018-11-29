
package services;

import java.util.Collection;
import java.util.Date;

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
	 * Checks customer authority (Req 10.1)
	 * 
	 * @param fixUpTask
	 * @return a new fix up task
	 */
	public FixUpTask create() {
		final FixUpTask res = new FixUpTask();
		return res;
	}

	/**
	 * Checks customer authority. (Req 10.1)
	 * 
	 * @param fixUpTask
	 * @return the fix up task saved in the database
	 */
	public FixUpTask save(final FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		final FixUpTask res;
		if (fixUpTask.getId() != 0) {
			final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTask.getId());
			Assert.isTrue(aux.getCustomer().getAccount().equals(userAccount));

			res = this.fixUpTaskRepository.save(fixUpTask);
		} else
			res = this.fixUpTaskRepository.save(fixUpTask);

		return res;

	}

	/**
	 * Checks customer authority. (Req 10.1)
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
	 * Deletes the fix up task whose id is passed as parameter checking customer authority. (Req 10.1)
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
	 * Deletes the fix up task passed as parameter checking customer authority. (Req 10.1)
	 * 
	 * @param fixUpTask
	 * 
	 */
	public void delete(final FixUpTask fixUpTask) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTask.getId());

		Assert.isTrue(aux.getCustomer().getAccount().equals(userAccount));

		this.fixUpTaskRepository.delete(aux);
	}

	/**
	 * Checks handy worker or customer authority (Req 10.1, 11.1)
	 * 
	 * @param CustomerId
	 * @return Collection of the fix up tasks related to a customer
	 */
	public Collection<FixUpTask> findFromCustomer(final int customerId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final Authority au1 = new Authority();
		au1.setAuthority(Authority.HANDYWORKER);

		final Authority au2 = new Authority();
		au2.setAuthority(Authority.CUSTOMER);

		Assert.isTrue(userAccount.getAuthorities().contains(au1) || userAccount.getAuthorities().contains(au2));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findFromCustomer(customerId);
		return res;
	}

	/**
	 * Checks handy worker authority (Req 11.1)
	 * 
	 * @param handyWorkerId
	 * @return Collection of all the fix up tasks
	 */
	public Collection<FixUpTask> findAsHandyWorker() {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final Authority au = new Authority();
		au.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(userAccount.getAuthorities().contains(au));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findAll();
		return res;
	}

	/**
	 * Checks handy workder authority (Req 11.2)
	 * 
	 * @param keyWord
	 * @param category
	 * @param warranty
	 * @param minPrice
	 * @param maxPrice
	 * @param open
	 * @param close
	 * @return a collection of fix up tasks filtered by the parameters given
	 */
	public Collection<FixUpTask> findByFilter(String keyWord, String category, String warranty, Double minPrice, Double maxPrice, Date open, Date close) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		//final Authority au = new Authority();
		//au.setAuthority(Authority.HANDYWORKER);

		//Assert.isTrue(userAccount.getAuthorities().contains(au));
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER));

		keyWord = (keyWord == null || keyWord.isEmpty()) ? "" : keyWord;
		category = (category == null || category.isEmpty()) ? "" : category;
		warranty = (warranty == null || warranty.isEmpty()) ? "" : warranty;
		minPrice = (minPrice == null) ? 0 : minPrice;
		maxPrice = (maxPrice == null) ? 0 : maxPrice;
		open = (Date) (open == null ? Date.UTC(2000, 0, 0, 0, 0, 0) : open);
		close = (Date) (close == null ? Date.UTC(2200, 0, 0, 0, 0, 0) : close);

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findByFilter(keyWord, category, warranty, minPrice, maxPrice, open, close);

		return res;
	}

	public int getNumberOfTickers(final String ticker) {
		return this.fixUpTaskRepository.getNumberOfTickers(ticker);
	}

}
