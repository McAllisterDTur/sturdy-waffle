
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	eRepository;


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
		return this.eRepository.save(e);
	}

	public EducationRecord findByCurricula(final int id) {
		return this.eRepository.findByCurricula(id);
	}
}
