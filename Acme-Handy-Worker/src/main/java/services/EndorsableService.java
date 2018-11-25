
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsableRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Endorsable;

@Service
@Transactional
public class EndorsableService {

	//Implemented repository
	@Autowired
	EndorsableRepository	endRepository;

	//Auxiliary
	@Autowired
	ActorService			actService;


	public Endorsable create() {
		final Endorsable e = new Endorsable();
		e.setBanned(false);
		e.setScore(0.0);
		return e;
	}

	public Collection<Endorsable> findAll() {
		return this.endRepository.findAll();
	}

	public Endorsable findOne(final int endId) {
		return this.endRepository.findOne(endId);
	}

	public Endorsable save(final Endorsable end) {
		//		final Actor a = this.actService.create();
		//		a.setAccount(end.getAccount());
		//		a.setAddress(end.getAddress());
		//		a.setBanned(end.getBanned());
		//		a.setEmail(end.getEmail());
		//		a.setMiddleName(end.getMiddleName());
		//		a.setName(end.getName());
		//		a.setPhone(end.getPhone());
		//		a.setPhotoURL(end.getPhotoURL());
		//		a.setSurname(end.getSurname());
		//		actService.save(a);
		return this.endRepository.save(end);
	}

	public Endorsable update(final Endorsable end) {
		Assert.isTrue(LoginService.getPrincipal().equals(end.getAccount()));
		final Endorsable toUpdate = this.endRepository.findOne(end.getId());
		toUpdate.setAddress(end.getAddress());
		toUpdate.setEmail(end.getEmail());
		toUpdate.setMiddleName(end.getMiddleName());
		toUpdate.setName(end.getName());
		toUpdate.setPhone(end.getName());
		toUpdate.setPhotoURL(end.getPhotoURL());
		toUpdate.setSurname(end.getSurname());
		return this.endRepository.save(toUpdate);
	}

	//Other methods

	public void ban(final Endorsable end) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		end.setBanned(true);
		this.endRepository.save(end);
	}
	public void unban(final Endorsable end) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		end.setBanned(false);
		this.endRepository.save(end);
	}

	//TODO:
	//public Endorsable computeScore(final Endorsable end) {
	//1. Coger palabras positivas
	//2. Coger palabras negativas
	//3. Contar palabras positivas en los endorsements
	//4. Contar palabras negativas en los endorsements
	//5. Positivas menos negativas
	//6. Palabras en total
	//7. Resta menos total
	//}
}
