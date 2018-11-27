
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculaRepository;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;


	public int getNumberOfTickers(final String ticker) {
		return this.curriculaRepository.getNumberOfTickers(ticker);
	}
}
