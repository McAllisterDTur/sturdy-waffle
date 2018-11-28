
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EducationRecordRepository;
import domain.Curricula;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	eRepository;
	@Autowired
	private CurriculaService			cService;


	public EducationRecord create() {
		return new EducationRecord();
	}

	public Collection<EducationRecord> findAll() {
		return this.eRepository.findAll();
	}

	public EducationRecord findOne(final int id) {
		return this.eRepository.findOne(id);
	}

	public EducationRecord save(final EducationRecord e) {
		final Curricula c = this.cService.findFromLoggedHandyWorker();
		final Collection<EducationRecord> es = c.getEducationRecord();
		if (e.getId() != 0)
			es.add(e);
		else
			for (final EducationRecord ei : es)
				if (ei.getId() == e.getId()) {
					es.remove(ei);
					es.add(e);
					break;
				}
		c.setEducationRecord(es);
		this.cService.save(c);
		return this.eRepository.save(e);
	}

	public Collection<EducationRecord> findByCurricula(final int id) {
		return this.eRepository.findByCurricula(id);
	}
}
