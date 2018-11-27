
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private MiscellaneousRecordRepository	mRepository;


	public MiscellaneousRecord create() {
		return new MiscellaneousRecord();
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.mRepository.findAll();
	}

	public MiscellaneousRecord findOne(final int id) {
		return this.mRepository.findOne(id);
	}

	public MiscellaneousRecord save(final MiscellaneousRecord m) {
		return this.mRepository.save(m);
	}

	public MiscellaneousRecord findByCurricula(final int id) {
		return this.mRepository.findByCurricula(id);
	}
}
