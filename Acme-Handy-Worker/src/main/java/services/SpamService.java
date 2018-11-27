
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Actor;
import domain.Configuration;

@Service
@Transactional
public class SpamService {

	//Auxiliary Services
	@Autowired
	private ConfigurationService	cService;
	@Autowired
	private ActorService			aService;


	public Boolean isSpam(final Actor a, final String s) {
		final Configuration c = (Configuration) this.cService.findAll().toArray()[0];
		final Collection<String> spamWords = c.getSpamWords();
		final Collection<String> sWords = new ArrayList<>(Arrays.asList(s.toLowerCase().split(" ")));
		final Boolean spam = sWords.removeAll(spamWords);
		a.setIsSuspicious(spam);
		this.aService.save(a);
		return spam;
	}

	public Boolean isSpam(final Actor a, final Collection<String> sCol) {
		Boolean spam = false;
		for (final String s : sCol) {
			spam = this.isSpam(a, s);
			if (spam)
				break;
		}
		a.setIsSuspicious(spam);
		this.aService.save(a);
		return spam;
	}
}
