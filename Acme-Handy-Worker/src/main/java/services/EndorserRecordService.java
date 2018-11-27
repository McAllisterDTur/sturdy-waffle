
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository	eRepository;


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
		return this.eRepository.save(e);
	}

	public EndorserRecord findByCurricula(final int id) {
		return this.eRepository.findByCurricula(id);
	}
}
