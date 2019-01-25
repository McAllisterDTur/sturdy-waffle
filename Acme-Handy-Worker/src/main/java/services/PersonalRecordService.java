
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curricula;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	@Autowired
	private PersonalRecordRepository	prRepository;
	@Autowired
	private CurriculaService			cService;


	public PersonalRecord create() {
		return new PersonalRecord();
	}

	public Collection<PersonalRecord> findAll() {
		return this.prRepository.findAll();
	}

	public PersonalRecord findOne(final int id) {
		return this.prRepository.findOne(id);
	}

	public PersonalRecord save(final PersonalRecord pr) {
		final Curricula c = this.cService.findFromLoggedHandyWorker();
		Assert.notNull(c, "Null curricula");
		c.setPersonalRecord(pr);
		this.cService.save(c);
		return this.prRepository.save(pr);
	}

	public PersonalRecord findByCurricula(final int id) {
		return this.prRepository.findByCurricula(id);
	}
}
