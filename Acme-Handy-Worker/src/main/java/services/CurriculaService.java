
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

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
		final Curricula c = new Curricula();
		c.setTicker(this.tickerService.getTicker());
		c.setEducationRecord(new ArrayList<EducationRecord>());
		c.setEndorserRecords(new ArrayList<EndorserRecord>());
		c.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		c.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		return c;
	}

	public Curricula save(final Curricula curricula) {
		final boolean au = AuthenticationUtility.checkAuthority(Authority.HANDYWORKER);
		Assert.isTrue(au);
		Assert.isNull(curricula, "You can't save a null curricula");
		final Curricula c = this.findFromHandyWorker(curricula.getHandyWorker());
		// If we have a curricula we can't create another
		if (c != null) {
			Assert.isTrue(c.getHandyWorker().equals(curricula.getHandyWorker()));
			Assert.isTrue(c.getId() == curricula.getId());
		}
		return this.curriculaRepository.save(curricula);
	}

	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public Curricula findFromLoggedHandyWorker() {
		final HandyWorker h = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		return this.curriculaRepository.getFromHandyWorker(h.getId());
	}

	public Curricula findFromHandyWorker(final HandyWorker h) {
		return this.curriculaRepository.getFromHandyWorker(h.getId());
	}

	public int getNumberOfTickers(final String ticker) {
		return this.curriculaRepository.getNumberOfTickers(ticker);
	}
}
