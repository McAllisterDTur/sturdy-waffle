
package services;

import java.util.ArrayList;
import java.util.Collection;

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


	public Box create(final Actor actor) {
		final Box b = new Box();
		b.setOwner(actor);
		b.setDeleteable(true);
		b.setMessages(new ArrayList<Message>());
		return b;
	}
	public Box save(final Box box) {
		// We search boxes from logged actor
		Box saved;
		if (box.getId() != 0) {
			final Box boxBD = this.findOne(box.getId());
			if (boxBD.getDeleteable() == false)
				boxBD.setMessages(box.getMessages());
			else {
				if ((LoginService.getPrincipal()).equals(box.getOwner().getAccount()))
					boxBD.setName(box.getName());

				boxBD.setMessages(box.getMessages());
			}
			saved = this.boxRepository.save(boxBD);
		} else
			saved = this.boxRepository.save(box);
		return saved;
	}
	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final int idBox) {
		final Box box = this.boxRepository.findOne(idBox);
		return box;
	}

	public void delete(final Box box) {
		Assert.isTrue((LoginService.getPrincipal().equals(box.getOwner().getAccount())), "Box not belong to the logged actor");
		Assert.isTrue((box.getDeleteable()), "Box undeleteable");
		if (box.getMessages() != null)
			for (final Message m : box.getMessages())
				this.messageService.deleteMessages(m, box);
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

	public void initializeDefaultBoxes(final Actor a) {
		final Actor owner = a;
		final Box in = this.create(a);
		in.setDeleteable(false);
		in.setName("IN");
		in.setOwner(owner);
		this.save(in);

		final Box trash = this.create(a);
		trash.setDeleteable(false);
		trash.setName("TRASH");
		trash.setOwner(owner);
		this.save(trash);

		final Box out = this.create(a);
		out.setDeleteable(false);
		out.setName("OUT");
		out.setOwner(owner);
		this.save(out);

		final Box spam = this.create(a);
		spam.setDeleteable(false);
		spam.setName("SPAM");
		spam.setOwner(owner);
		this.save(spam);
	}

	public Collection<Box> findByOwner(final int actorId) {
		return this.boxRepository.boxesByActor(actorId);
	}

	public Collection<Box> findByName(final int actorId, final String name) {
		return this.boxRepository.boxByName(actorId, name);
	}

}
