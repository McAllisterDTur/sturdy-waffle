
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorsableRepository;
import domain.Application;
import domain.Configuration;
import domain.Endorsable;
import domain.Endorsement;

@Service
@Transactional
public class EndorsableService {

	//Implemented repository
	@Autowired
	EndorsableRepository	endRepository;

	//Auxiliary
	@Autowired
	ConfigurationService	confService;
	@Autowired
	EndorsementService		endorService;
	@Autowired
	FixUpTaskService		futService;
	@Autowired
	ApplicationService		appService;


	public Endorsable findOne(final int id) {
		return this.endRepository.findOne(id);
	}

	//Other methods

	public Endorsable computeScore(final Endorsable end) {
		final Configuration conf = (Configuration) this.confService.findAll().toArray()[0];
		final Collection<String> pWs = conf.getPositiveWords();
		final Collection<String> nWs = conf.getNegativeWords();
		final Collection<Endorsement> endorsements = this.endorService.findAllEndorsable(end.getId());
		final Collection<String> allWords = new ArrayList<>();
		for (final Endorsement e : endorsements)
			allWords.addAll(Arrays.asList(e.getComment().toLowerCase().split(" ")));
		Integer p = 0;
		Integer n = 0;
		for (final String s : pWs)
			if (allWords.contains(s))
				p++;
		for (final String s : nWs)
			if (allWords.contains(s))
				n++;
		final Double score = p - n + 0.0;
		final Double total = p + n + 0.0;
		final Double normalized = score / total;
		end.setScore(normalized);
		return this.endRepository.save(end);
	}

	public Collection<Endorsable> findAllWorkedWith(final Integer id) {
		final Endorsable e = this.findOne(id);
		final String role = e.getAccount().getAuthorities().iterator().next().getAuthority();
		System.out.println(role);
		final Collection<Endorsable> res = new ArrayList<>();
		if (role.equals("HANDYWORKER"))
			//			for (final Application app : this.appService.findAllWorker(id))
			//				if (app.getStatus().equals("ACCEPTED"))
			//					res.add(app.getFixUpTask().getCustomer());
			res.addAll(this.endRepository.findAllCustomerWhoWorkedWithHandy(id));
		else if (role.equals("CUSTOMER")) {
			for (final Application app : this.appService.findAllCustomer(id))
				if (app.getStatus().equals("ACCEPTED"))
					res.add(app.getHandyWorker());
		} else
			throw new IllegalArgumentException("An endorsable is either a HandyWorker or a Customer");
		return res;
	}
}
