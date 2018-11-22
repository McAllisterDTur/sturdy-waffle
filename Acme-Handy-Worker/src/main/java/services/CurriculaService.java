
package services;

import org.springframework.stereotype.Service;

import repositories.CurriculaRepository;

@Service
public class CurriculaService {

	private CurriculaRepository	curriculaRepository;


	public int getNumberOfTickers(final String ticker) {
		return this.curriculaRepository.getNumberOfTickers(ticker);
	}
}
