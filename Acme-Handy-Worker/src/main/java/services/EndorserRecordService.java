
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curricula;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository	eRepository;
	@Autowired
	private CurriculaService			cService;


	public EndorserRecord create() {
		return new EndorserRecord();
	}

	public Collection<EndorserRecord> findAll() {
		return this.eRepository.findAll();
	}

	public EndorserRecord findOne(final int id) {
		return this.eRepository.findOne(id);
	}

	public EndorserRecord save(final EndorserRecord e) {
		final Curricula c = this.cService.findFromLoggedHandyWorker();
		Assert.notNull(c);
		Collection<EndorserRecord> es;
		try {
			es = c.getEndorserRecords();
		} catch (final NullPointerException ex) {
			es = new ArrayList<>();
		}

		if (e.getId() == 0)
			es.add(e);
		else
			for (final EndorserRecord ei : es)
				if (ei.getId() == e.getId()) {
					es.remove(ei);
					es.add(e);
					break;
				}
		c.setEndorserRecords(es);

		this.cService.save(c);
		return this.eRepository.save(e);
	}
	public Collection<EndorserRecord> findByCurricula(final int id) {
		return this.eRepository.findByCurricula(id);
	}
}
