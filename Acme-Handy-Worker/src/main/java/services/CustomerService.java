
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepo;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private BoxService			boxService;
	@Autowired
	private FinderService		finderService;

	private UserAccount			account;


	public Customer create() {

		final Customer res = new Customer();
		final UserAccount a = this.userAccountService.create();

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		a.addAuthority(auth);
		res.setAccount(a);
		res.setFixUpTasks(new ArrayList<FixUpTask>());

		return res;
	}

	public Customer save(final Customer c) {
		Assert.notNull(c);
		Customer result;
		if (c.getId() == 0) {
			final UserAccount account = c.getAccount();
			final UserAccount savedAccount = this.userAccountService.save(account);
			c.setAccount(savedAccount);
			result = this.customerRepo.save(c);
			this.boxService.initializeDefaultBoxes(result);
		} else
			result = this.customerRepo.save(c);
		return result;
	}

	public Customer findOne(final int id) {
		Customer res;
		Assert.isTrue(id != 0);
		res = this.customerRepo.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Customer> findCustomerMaxAverage() {

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));
		final List<Customer> res = (ArrayList<Customer>) this.customerRepo.findCustomerMaxAverage();
		//		final Comparator<Customer> comp = new Comparator<Customer>() {
		//
		//			@Override
		//			public int compare(final Customer o1, final Customer o2) {
		//
		//				int nAo1 = 0;
		//				final int nAo2 = 0;
		//
		//				final FixUpTask[] t1 = (FixUpTask[]) o1.getFixUpTasks().toArray();
		//				final FixUpTask[] t2 = (FixUpTask[]) o2.getFixUpTasks().toArray();
		//
		//				for (int i = 0; i < o1.getFixUpTasks().size(); i++)
		//					nAo1 = nAo1 + t1[i].getApplications().size();
		//				for (int i = 0; i < o1.getFixUpTasks().size(); i++)
		//					nAo1 = nAo1 + t2[i].getApplications().size();
		//
		//				System.out.println(nAo1);
		//				System.out.println(nAo2);
		//
		//				if (nAo1 > nAo2)
		//					return 1;
		//				else if (nAo1 < nAo2)
		//					return -1;
		//				else
		//					return 0;
		//			}
		//
		//		};
		//		Collections.sort(res, comp);
		return res;

	}
	public Collection<Customer> findCustomerMaxComplaintsTop3() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));

		final Collection<Customer> customers = this.customerRepo.findCustomerMaxComplaints();
		final Collection<Customer> res = new ArrayList<>();
		int i = 0;
		for (final Customer c : customers) {
			if (i > 2)
				break;
			res.add(c);
			i++;
		}
		return res;
	}

	public Collection<Customer> findAll() {
		return this.customerRepo.findAll();
	}

	public Customer actorToCustomer(final Actor a) {
		final Customer res = new Customer();
		res.setAccount(a.getAccount());
		res.setAddress(a.getAddress());
		res.setFixUpTasks(new ArrayList<FixUpTask>());
		res.setBanned(a.getBanned());
		res.setEmail(a.getEmail());
		res.setId(a.getId());
		res.setIsSuspicious(a.getIsSuspicious());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhone(a.getPhone());
		res.setPhotoURL(a.getPhotoURL());
		res.setScore(0d);
		res.setSurname(a.getSurname());
		res.setVersion(a.getVersion());
		res.setIsSuspicious(false);
		return res;
	}
}
