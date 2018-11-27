
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository	prRepository;


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
		return this.prRepository.save(pr);
	}

	public ProfessionalRecord findByCurricula(final int id) {
		return this.prRepository.findByCurricula(id);
	}
}
