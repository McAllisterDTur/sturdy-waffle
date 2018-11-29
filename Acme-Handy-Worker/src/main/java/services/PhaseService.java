
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.Phase;

@Transactional
@Service
public class PhaseService {

	@Autowired
	private PhaseRepository	repo;

	private UserAccount		account;


	public Phase create() {
		return new Phase();
	}

	public Phase save(final Phase phase) {
		this.account = LoginService.getPrincipal();
		//Verificamos que el creador de la phase sea un HANDYWORKER
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));
		//Verificamos que el creador de la phase sea el due�o de la application
		Assert.isTrue(phase.getApplication().getHandyWorker().getAccount().getId() == this.account.getId());

		return this.repo.save(phase);
	}

	public Phase findOne(final int id) {

		this.account = LoginService.getPrincipal();
		Assert.notNull(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));

		final Phase res = this.repo.findOne(id);
		Assert.isTrue(res.getApplication().getHandyWorker().getAccount().getId() == this.account.getId());

		return res;

	}

	public Collection<Phase> findAllWorker(final HandyWorker worker) {

		Assert.notNull(worker);

		this.account = LoginService.getPrincipal();
		Assert.isTrue(worker.getAccount().getId() == this.account.getId());

		return this.repo.findAllWorker(worker.getId());
	}

	/**
	 * 
	 * @deprecated
	 */
	@Deprecated
	public Phase update(final Phase p) {
		Assert.notNull(p);

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getId() == p.getApplication().getHandyWorker().getId());

		final Phase original = this.repo.findOne(p.getId());

		original.setDescription(p.getDescription());
		original.setEndTime(p.getEndTime());
		original.setStartTime(p.getStartTime());
		original.setTitle(p.getTitle());

		return this.repo.save(original);
	}

	public void delete(final Phase p) {
		Assert.notNull(p);

		this.account = LoginService.getPrincipal();
		Assert.isTrue(p.getApplication().getHandyWorker().getAccount().getId() == this.account.getId());

		this.repo.delete(p);

	}

}
