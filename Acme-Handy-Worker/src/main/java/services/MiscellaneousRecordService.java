
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousRecordRepository;
import domain.Curricula;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private MiscellaneousRecordRepository	mRepository;
	@Autowired
	private CurriculaService				cService;


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
		final Curricula c = this.cService.findFromLoggedHandyWorker();
		final Collection<MiscellaneousRecord> ms = c.getMiscellaneousRecords();
		if (m.getId() != 0)
			ms.add(m);
		else
			for (final MiscellaneousRecord mi : ms)
				if (mi.getId() == m.getId()) {
					ms.remove(mi);
					ms.add(m);
					break;
				}
		c.setMiscellaneousRecords(ms);
		this.cService.save(c);
		return this.mRepository.save(m);
	}

	public Collection<MiscellaneousRecord> findByCurricula(final int id) {
		return this.mRepository.findByCurricula(id);
	}
}
