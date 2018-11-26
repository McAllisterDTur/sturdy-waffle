
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsableRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Configuration;
import domain.Endorsable;

@Service
@Transactional
public class EndorsableService {

	//Implemented repository
	@Autowired
	EndorsableRepository	endRepository;

	//Auxiliary
	@Autowired
	ActorService			actService;
	@Autowired
	ConfigurationService	confService;


	public Endorsable create() {
		final Endorsable e = new Endorsable();
		e.setBanned(false);
		e.setScore(0.0);
		return e;
	}

	public Collection<Endorsable> findAll() {
		return this.endRepository.findAll();
	}

	public Endorsable findOne(final int endId) {
		return this.endRepository.findOne(endId);
	}

	public Endorsable save(final Endorsable end) {
		return this.endRepository.save(end);
	}

	public Endorsable update(final Endorsable end) {
		Assert.isTrue(LoginService.getPrincipal().equals(end.getAccount()));
		final Endorsable toUpdate = this.endRepository.findOne(end.getId());
		toUpdate.setAddress(end.getAddress());
		toUpdate.setEmail(end.getEmail());
		toUpdate.setMiddleName(end.getMiddleName());
		toUpdate.setName(end.getName());
		toUpdate.setPhone(end.getName());
		toUpdate.setPhotoURL(end.getPhotoURL());
		toUpdate.setSurname(end.getSurname());
		return this.endRepository.save(toUpdate);
	}

	//Other methods

	public void ban(final Endorsable end) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		end.setBanned(true);
		this.endRepository.save(end);
	}
	public void unban(final Endorsable end) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		end.setBanned(false);
		this.endRepository.save(end);
	}

	public Endorsable computeScore(final Endorsable end) {
		final Configuration conf = (Configuration) this.confService.findAll().toArray()[0];
		final Collection<String> pWs = conf.getPositiveWords();
		final Collection<String> nWs = conf.getNegativeWords();

		final Integer p = 0;
		final Integer n = 0;
		for (final String s : pWs) {
			//TODO: necesito el endorsement service
		}
		for (final String s : nWs) {
			//TODO: necesito el endorsement service
		}
		final Double score = p - n + 0.0;
		final Double total = p + n + 0.0;
		final Double normalized = score / total;
		end.setScore(normalized);
		return this.endRepository.save(end);
	}
}
