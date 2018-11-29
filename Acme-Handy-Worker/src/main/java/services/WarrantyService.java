
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.Authority;
import utilities.AuthenticationUtility;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	@Autowired
	private WarrantyRepository	wRepo;


	/**
	 * Creates a new warranty (Req 12.2)
	 * 
	 * @return a new empty warranty
	 */
	public Warranty create() {
		final Warranty w = new Warranty();
		w.setLaw(new ArrayList<String>());
		return w;
	}

	/**
	 * Checks administrator authority. Saves or updates a warranty.
	 * Only warranties in draft mode can be updated. (Req 12.2)
	 * 
	 * @param warranty
	 * @return the warranty saved in the database
	 */
	public Warranty save(final Warranty warranty) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		// Check authority
		Assert.isTrue(hasAu);

		if (warranty.getId() != 0) {

			// We are updating 
			// Check draft, but the one on ddbb, otherwise you couldn't change draft
			final Warranty aux = this.wRepo.findOne(warranty.getId());
			Assert.isTrue(aux.isDraft());
		}

		// We are saving			
		final Warranty res = this.wRepo.save(warranty);

		return res;
	}
	/**
	 * Checks administrator authority (Req 12.2)
	 * 
	 * @return Collection of all the warranties
	 */
	public Collection<Warranty> findAll() {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		// Check authority
		Assert.isTrue(hasAu);
		return this.wRepo.findAll();
	}

	/**
	 * Checks administrator authority. (Req 12.2)
	 * 
	 * @param warrantyId
	 * @return the warranty whose id is the one passed as parameter
	 */
	public Warranty findOne(final int warrantyId) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		// Check authority
		Assert.isTrue(hasAu);
		return this.wRepo.findOne(warrantyId);
	}
	/**
	 * Deletes the warranty passed as parameter checking administrator authority.
	 * Only warranties in draft mode can be deleted. (Req 12.2)
	 * 
	 * @param warranty
	 */
	public void delete(final Warranty warranty) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		// Check authority
		Assert.isTrue(hasAu);
		// Check draft
		Assert.isTrue(warranty.isDraft());
		this.wRepo.delete(warranty);

	}
	/**
	 * Deletes the warranty whose id is passed as parameter checking administrator authority.
	 * Only warranties in draft mode can be deleted. (Req 12.2)
	 * 
	 * @param warranty
	 */
	public void delete(final int warrantyId) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(hasAu);
		// Check authority
		Assert.isTrue(hasAu);
		// Check draft
		final Warranty w = this.wRepo.findOne(warrantyId);
		Assert.isTrue(w.isDraft());

		this.wRepo.delete(warrantyId);

	}

}
