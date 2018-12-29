
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
import domain.Message;

@Service
@Transactional
public class BoxService {

	//We create the repository
	@Autowired
	private BoxRepository	boxRepository;

	//Auxiliary services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	//CRUDs

	public Box create(final Actor actor) {
		final Box b = new Box();
		b.setOwner(actor);
		b.setDeleteable(true);
		b.setMessages(new ArrayList<Message>());
		return b;
	}
	public Box save(final Box box) {
		final List<String> names = this.allBoxNames();
		if (box.getId() == 0)
			Assert.isTrue(names == null || !names.contains(box.getName()));
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
		for (final Message m : box.getMessages())
			this.messageService.delete(m);
		this.boxRepository.delete(box);
	}

	//Other requirements

	public void initializeDefaultBoxes() {
		final UserAccount ownerAccount = LoginService.getPrincipal();
		final Actor owner = this.actorService.findByUserAccountId(ownerAccount.getId());
		final Box in = this.create(owner);
		in.setDeleteable(false);
		in.setName("IN");
		this.save(in);

		final Box trash = this.create(owner);
		trash.setDeleteable(false);
		trash.setName("TRASH");
		this.save(trash);

		final Box out = this.create(owner);
		out.setDeleteable(false);
		out.setName("OUT");
		this.save(out);

		final Box spam = this.create(owner);
		spam.setDeleteable(false);
		spam.setName("SPAM");
		this.save(spam);
	}

	public Collection<Box> findByOwner(final int actorId) {
		return this.boxRepository.boxesByActor(actorId);
	}

	public Box findByName(final int actorId, final String name) {
		return this.boxRepository.boxByName(actorId, name);
	}

	//Auxiliary methods

	private List<String> allBoxNames() {
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		List<String> names = new ArrayList<>();
		try {
			for (final Box b : this.boxRepository.boxesByActor(owner.getId()))
				names.add(b.getName());
		} catch (final NullPointerException e) {
			names = null;
		}

		return names;
	}
}
