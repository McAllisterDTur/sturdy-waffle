
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private TickerService		tickerService;
	@Autowired
	private SpamService			spamService;


	/**
	 * Creates a new fix up task(Req 10.1)
	 * 
	 * @return a new empty fix up task
	 */
	public FixUpTask create() {
		final FixUpTask res = new FixUpTask();
		res.setComplaints(new HashSet<Complaint>());
		res.setApplications(new HashSet<Application>());
		return res;
	}

	/**
	 * Checks customer authority. Saves or updates a fix up task(Req 10.1)
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

		FixUpTask res;

		//comprobamos que la warranty NO est� en draft mode
		Assert.isTrue(!fixUpTask.getWarranty().getDraft());
		if (fixUpTask.getId() != 0) {
			// Ya est� en base de datos
			final FixUpTask aux = this.fixUpTaskRepository.findOne(fixUpTask.getId());
			Assert.isTrue(aux.getCustomer().getAccount().equals(userAccount));
			Assert.isTrue(fixUpTask.getCustomer().getAccount().equals(aux.getCustomer().getAccount()));
			res = this.fixUpTaskRepository.save(fixUpTask);
		} else {
			fixUpTask.setTicker(this.tickerService.getTicker());
			fixUpTask.setPublishTime(new Date());
			fixUpTask.setCustomer((Customer) this.actorService.findByUserAccountId(userAccount.getId()));
			res = this.fixUpTaskRepository.save(fixUpTask);
		}
		this.spamService.isSpam(this.actorService.findByUserAccountId(userAccount.getId()), res.getDescription());
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
		if (res != null)
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
	 * Checks customer authority (Req 10.1)
	 * 
	 * @param CustomerId
	 * @return Collection of the fix up tasks related to the logged customer
	 */
	public Collection<FixUpTask> findFromLoggedCustomer() {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final Authority au = new Authority();
		au.setAuthority(Authority.CUSTOMER);

		Assert.isTrue(userAccount.getAuthorities().contains(au));

		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<FixUpTask> res = this.fixUpTaskRepository.findFromCustomer(c.getId());
		return res;
	}
	/**
	 * Checks admin authority (Req 12.5.1)
	 * 
	 * @return Collection of fix up task count statistics
	 */
	public Collection<Double> avgMinMaxDevFixUpTaskCount() {
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);

		return this.fixUpTaskRepository.avgMinMaxDevFixUpTaskCount();
	}

	/**
	 * Checks admin authority (Req 38.5.3)
	 * 
	 * @return Ratio of fix up tasks with complaints
	 */
	public Double ratioFixUpTaskComplaint() {
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);

		return this.fixUpTaskRepository.ratioFixUpTaskComplaint();
	}

	/**
	 * Checks admin authority (Req 12.5.3)
	 * 
	 * @return Collection of fix up task price statistics
	 */
	public Collection<Double> avgMinMaxDevFixUpTaskPrice() {
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);

		return this.fixUpTaskRepository.avgMinMaxDevFixUpTaskPrice();
	}
	/**
	 * Checks handy worker authority (Req 11.1)
	 * 
	 * @param CustomerId
	 * @return Collection of the fix up tasks related to a customer
	 */
	public Collection<FixUpTask> findFromCustomer(final int customerId) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final Authority au1 = new Authority();
		au1.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(userAccount.getAuthorities().contains(au1));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findFromCustomer(customerId);
		return res;
	}

	/**
	 * Checks handy worker authority (Req 11.1)
	 * 
	 * @return Collection of all the fix up tasks
	 */
	public Collection<FixUpTask> findAll() {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		final Authority au = new Authority();
		au.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(userAccount.getAuthorities().contains(au));

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findAll();
		return res;
	}

	/**
	 * Checks handy worker authority (Req 11.2)
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

		final Authority au = new Authority();
		au.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(userAccount.getAuthorities().contains(au));

		keyWord = (keyWord == null || keyWord.isEmpty()) ? "" : keyWord;
		category = (category == null || category.isEmpty()) ? "" : category;
		warranty = (warranty == null || warranty.isEmpty()) ? "" : warranty;
		minPrice = (minPrice == null) ? 0 : minPrice;
		maxPrice = (maxPrice == null) ? 1000000 : maxPrice;

		final Date d1 = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
		final Date d2 = new GregorianCalendar(2200, Calendar.JANUARY, 1).getTime();
		open = open == null ? d1 : open;
		close = close == null ? d2 : close;

		final Collection<FixUpTask> res = this.fixUpTaskRepository.findByFilter(keyWord, category, warranty, minPrice, maxPrice, open, close);

		return res;
	}

	public int getNumberOfTickers(final String ticker) {
		return this.fixUpTaskRepository.getNumberOfTickers(ticker);
	}

	public Collection<FixUpTask> getByCategory(final int categoryId) {
		return this.fixUpTaskRepository.getFixUpTasksByCategory(categoryId);
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

}
