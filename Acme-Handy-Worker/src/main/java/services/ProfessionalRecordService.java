
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalRecordRepository;
import domain.Curricula;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository	prRepository;
	@Autowired
	private CurriculaService				cService;


	public ProfessionalRecord create() {
		return new ProfessionalRecord();
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.prRepository.findAll();
	}

	public ProfessionalRecord findOne(final int id) {
		return this.prRepository.findOne(id);
	}

	public ProfessionalRecord save(final ProfessionalRecord pr) {
		final Curricula c = this.cService.findFromLoggedHandyWorker();
		final Collection<ProfessionalRecord> prs = c.getProfessionalRecords();
		if (pr.getId() != 0)
			prs.add(pr);
		else
			for (final ProfessionalRecord pri : prs)
				if (pri.getId() == pr.getId()) {
					prs.remove(pri);
					prs.add(pr);
					break;
				}
		c.setProfessionalRecords(prs);
		this.cService.save(c);
		return this.prRepository.save(pr);
	}
	public Collection<ProfessionalRecord> findByCurricula(final int id) {
		return this.prRepository.findByCurricula(id);
	}
}
