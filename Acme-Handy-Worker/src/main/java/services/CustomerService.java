
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepo;

	private UserAccount			account;


	public Customer create() {

		final Customer res = new Customer();

		res.setFixUpTasks(new ArrayList<FixUpTask>());

		return res;
	}

	public Customer save(final Customer c) {

		Assert.notNull(c);

		if (c.getId() <= 0)
			c.setScore(0.0);

		return this.customerRepo.save(c);
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

		return this.customerRepo.findCustomerMaxAverage();

	}

	public Customer findOneHandyWorker(final int id) {
		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));

		Customer res;
		Assert.isTrue(id != 0);

		res = this.customerRepo.findOne(id);
		Assert.notNull(res);
		return res;
	}

}
