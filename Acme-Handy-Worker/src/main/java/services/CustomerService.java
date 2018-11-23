
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepo;


	public Customer create() {

		return new Customer();

	}

	public Customer save(final Customer c) {
		Assert.notNull(c);
		return this.customerRepo.save(c);
	}

}
