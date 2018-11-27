
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Curricula;
import domain.HandyWorker;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private TickerService		tickerService;


	public Curricula create() {
		return new Curricula();
	}

	public Curricula save(final Curricula curricula) {
		final boolean au = AuthenticationUtility.checkAuthority(Authority.HANDYWORKER);
		Assert.isTrue(au);
		final Curricula c = this.findFromLoggedHandyWorker();
		// If we have a curricula we can't create another
		if (c.getId() != 0) {
			Assert.isTrue(c.getHandyWorker().equals(curricula.getHandyWorker()));
			Assert.isTrue(c.getId() == curricula.getId());
		}
		curricula.setTicker(this.tickerService.getTicker());
		curricula.setHandyWorker((HandyWorker) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId()));
		return this.curriculaRepository.save(curricula);
	}

	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public Curricula findFromLoggedHandyWorker() {
		final HandyWorker h = (HandyWorker) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		return this.curriculaRepository.findOne(h.getId());
	}

	public int getNumberOfTickers(final String ticker) {
		return this.curriculaRepository.getNumberOfTickers(ticker);
	}
}
