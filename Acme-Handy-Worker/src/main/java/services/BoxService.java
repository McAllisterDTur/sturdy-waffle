
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
import domain.Box;

@Service
@Transactional
public class BoxService {

	//We create the repository
	@Autowired
	BoxRepository	boxRepository;


	//CRUDs

	public Box create() {
		return new Box();
	}

	public Box save(final Box box) {
		final List<String> names = this.allBoxNames();
		Assert.isTrue(names.contains(box.getName()));
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
		Assert.isTrue(!box.getDeleteable());
		this.boxRepository.delete(box);
	}

	//Other requirements

	public void initializeDefaultBoxes() {
		final UserAccount ownerid = LoginService.getPrincipal();
		//TODO: Necesito coger un actor a partir de su useraccount

		final Box in = this.create();
		in.setDeleteable(false);
		in.setName("IN");
		//TODO: in.setOwner(owner);
		this.save(in);

		final Box trash = this.create();
		trash.setDeleteable(false);
		trash.setName("TRASH");
		//TODO: in.setOwner(owner);
		this.save(trash);

		final Box out = this.create();
		out.setDeleteable(false);
		out.setName("OUT");
		//TODO: in.setOwner(owner);
		this.save(out);

		final Box spam = this.create();
		spam.setDeleteable(false);
		spam.setName("SPAM");
		//TODO: in.setOwner(owner);
		this.save(spam);
	}

	public Collection<Box> findByOwner(final int actorId) {
		return this.boxRepository.boxesByActor(actorId);
	}

	//Auxiliary methods

	private List<String> allBoxNames() {
		final List<String> names = new ArrayList<>();
		for (final Box b : this.boxRepository.findAll())
			names.add(b.getName());
		return names;
	}
}
