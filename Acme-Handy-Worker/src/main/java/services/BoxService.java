
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;

@Service
@Transactional
public class BoxService {

	//We create the repository
	@Autowired
	BoxRepository	boxRepository;

	//Auxiliary services
	@Autowired
	ActorService	actorService;


	//CRUDs

	public Box create() {
		return new Box();
	}

	public Box save(final Box box) {
		final List<String> names = this.allBoxNames();
		Assert.isTrue(!names.contains(box.getName()));
		final Box saved = this.boxRepository.save(box);
		return saved;
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final int idBox) {
		return this.boxRepository.findOne(idBox);
	}

	public void delete(final Box box) {
		Assert.isTrue(LoginService.getPrincipal().equals(box.getOwner().getAccount()));
		Assert.isTrue(box.getDeleteable());
		this.boxRepository.delete(box);
	}

	//Other requirements

	public void initializeDefaultBoxes() {
		final UserAccount ownerAccount = LoginService.getPrincipal();
		final Actor owner = this.actorService.findByUserAccountId(ownerAccount.getId());
		final Box in = this.create();
		in.setDeleteable(false);
		in.setName("IN");
		in.setOwner(owner);
		this.save(in);

		final Box trash = this.create();
		trash.setDeleteable(false);
		trash.setName("TRASH");
		trash.setOwner(owner);
		this.save(trash);

		final Box out = this.create();
		out.setDeleteable(false);
		out.setName("OUT");
		out.setOwner(owner);
		this.save(out);

		final Box spam = this.create();
		spam.setDeleteable(false);
		spam.setName("SPAM");
		spam.setOwner(owner);
		this.save(spam);
	}

	public Collection<Box> findByOwner(final int actorId) {
		return this.boxRepository.boxesByActor(actorId);
	}

	//Auxiliary methods

	private List<String> allBoxNames() {
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final List<String> names = new ArrayList<>();
		for (final Box b : this.boxRepository.boxesByActor(owner.getId()))
			names.add(b.getName());
		return names;
	}
}
