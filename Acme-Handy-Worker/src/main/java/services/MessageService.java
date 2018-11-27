
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//The repository that we are managing
	@Autowired
	MessageRepository	msgRepository;

	//Auxiliary repositories
	@Autowired
	ActorService		aService;
	@Autowired
	BoxService			bService;


	//CRUDs
	public Message create() {
		return new Message();
	}

	public Message save(final Message msg) {
		msg.setSender(this.aService.findByUserAccountId(LoginService.getPrincipal().getId()));
		return this.msgRepository.save(msg);
	}

	public Collection<Message> findAll() {
		final Collection<Message> all = this.msgRepository.findAll();
		final Collection<Message> res = new ArrayList<>();
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		for (final Message m : all)
			if (m.getSender().equals(logged) || m.getReciever().equals(logged))
				res.add(m);
		return res;
	}

	public Message findOne(final int msgId) {
		final Message res = this.msgRepository.findOne(msgId);
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(res.getSender().equals(logged) || res.getReciever().equals(logged));
		return res;
	}

	public Message update(final Message msg) {
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(msg.getSender().equals(logged));
		final Message toupd = this.findOne(msg.getId());
		toupd.setBody(msg.getBody());
		toupd.setBoxes(msg.getBoxes());
		toupd.setPriority(msg.getPriority());
		toupd.setSubject(msg.getSubject());
		toupd.setTags(msg.getTags());

		return this.msgRepository.save(toupd);
	}

	public void delete(final Message msg) {
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(msg.getSender().equals(logged) || msg.getReciever().equals(logged));
		final Collection<Box> boxesDeletor = this.bService.findByOwner(logged.getId());
		final Collection<Box> boxesMsg = msg.getBoxes();
		final Box trash = this.bService.findByName(logged.getId(), "TRASH");
		final Collection<Message> trashMsg = this.findByBox(trash);
		for (final Box b : boxesDeletor) {
			final Collection<Message> msgs = this.findByBox(b);
			if (msgs.contains(msg)) {
				msgs.remove(msg);
				b.setMessages(msgs);
				this.bService.save(b);
				boxesMsg.remove(msg);
			}
		}
		boxesMsg.add(trash);
		msg.setBoxes(boxesMsg);
		trashMsg.add(msg);
		trash.setMessages(trashMsg);
		this.msgRepository.save(msg);
		this.bService.save(trash);

	}
	//Other methods

	public void deleteFromTrash(final Message msg) {
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(msg.getSender().equals(logged) || msg.getReciever().equals(logged));
		final Message tosupr = this.findOne(msg.getId());
		final Box trash = this.bService.findByName(logged.getId(), "TRASH");
		final Collection<Message> msgs = this.findByBox(trash);
		final Collection<Box> boxesMsg = tosupr.getBoxes();
		if (msgs.contains(tosupr)) {
			msgs.remove(tosupr);
			trash.setMessages(msgs);
			this.bService.save(trash);
			boxesMsg.remove(msg);
			tosupr.setBoxes(boxesMsg);
		}
		final Message deleted = this.msgRepository.save(tosupr);
		if (deleted.getBoxes().isEmpty())
			this.msgRepository.delete(deleted);
	}

	public Message send(final Message msg, final Actor receiver) {
		final Actor sender = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (msg.getSender() != null)
			Assert.isTrue(sender.equals(msg.getSender()));
		else
			msg.setSender(sender);
		msg.setReciever(receiver);
		final Message saved = this.msgRepository.save(msg);

		final Box in = this.bService.findByName(receiver.getId(), "IN");
		Collection<Message> inMsg = in.getMessages();
		if (inMsg == null)
			inMsg = new ArrayList<>();
		inMsg.add(saved);
		in.setMessages(inMsg);
		this.bService.save(in);

		final Box out = this.bService.findByName(sender.getId(), "OUT");
		Collection<Message> outMsg = out.getMessages();
		if (outMsg == null)
			outMsg = new ArrayList<>();
		outMsg.add(saved);
		out.setMessages(outMsg);
		this.bService.save(out);

		return saved;
	}

	public Collection<Message> findByBox(final Box b) {
		try {
			return this.msgRepository.findByBox(b.getId());
		} catch (final NullPointerException e) {
			return null;
		}

	}
}
