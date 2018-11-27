
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorsableRepository;
import domain.Configuration;
import domain.Endorsable;

@Service
@Transactional
public class EndorsableService {

	//Implemented repository
	@Autowired
	EndorsableRepository	endRepository;

	//Auxiliary
	@Autowired
	ConfigurationService	confService;


	//Other methods

	public Endorsable computeScore(final Endorsable end) {
		final Configuration conf = (Configuration) this.confService.findAll().toArray()[0];
		final Collection<String> pWs = conf.getPositiveWords();
		final Collection<String> nWs = conf.getNegativeWords();

		final Integer p = 0;
		final Integer n = 0;
		for (final String s : pWs) {
			//TODO: necesito el endorsement service
		}
		for (final String s : nWs) {
			//TODO: necesito el endorsement service
		}
		final Double score = p - n + 0.0;
		final Double total = p + n + 0.0;
		final Double normalized = score / total;
		end.setScore(normalized);
		return this.endRepository.save(end);
	}
}
