
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepo;

	private UserAccount			account;


	public Report create() {
		return new Report();
	}

	public Report save(final Report report) {
		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.REFEREE));

		Assert.isTrue(report.getReferee().getAccount().equals(this.account));
		if (report.getId() != 0)
			Assert.isTrue(!report.getIsFinal());

		return this.reportRepo.save(report);
	}

	public Report findOne(final int reportId) {
		Assert.isTrue(reportId > 0);

		this.account = LoginService.getPrincipal();

		final Report res = this.reportRepo.findOne(reportId);

		Assert.isTrue((AuthenticationUtility.checkAuthority(Authority.REFEREE)) || ((AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) && res.getIsFinal()));

		return res;
	}

	//	public Collection<Report> findReportsReferee(final Referee ref) {
	//
	//		this.account = LoginService.getPrincipal();
	//		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE));
	//
	//		Assert.notNull(ref);
	//		Assert.isTrue(ref.getAccount().getId() == this.account.getId());
	//
	//		return this.reportRepo.findAllReferee(ref.getId());
	//
	//	}

}
