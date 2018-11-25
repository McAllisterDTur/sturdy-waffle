
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
		final Message tosupr = this.findOne(msg.getId());
		final Collection<Box> msgboxes = tosupr.getBoxes();
		for (final Box b : tosupr.getBoxes())
			if (b.getOwner().equals(logged)) {
				b.getMessages().remove(tosupr);
				msgboxes.remove(b);
			}
		tosupr.setBoxes(msgboxes);
		final Collection<Box> allboxes = this.bService.findByOwner(logged.getId());
		Box trash = this.bService.create();
		for (final Box b : allboxes)
			if (b.getName().equals("TRASH"))
				trash = b;
		final Collection<Box> bs = new ArrayList<>();
		bs.add(trash);
		trash.getMessages().add(tosupr);
		tosupr.setBoxes(bs);
		this.msgRepository.save(tosupr);
		this.bService.save(trash);
	}
	//Other methods

	public void deleteFromTrash(final Message msg) {
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(msg.getSender().equals(logged) || msg.getReciever().equals(logged));
		final Message tosupr = this.findOne(msg.getId());
		for (final Box b : tosupr.getBoxes())
			if (b.getOwner().equals(logged))
				tosupr.getBoxes().remove(b);
		final Message deleted = this.msgRepository.save(tosupr);
		if (deleted.getBoxes().isEmpty())
			this.msgRepository.delete(deleted);
	}

	public Message send(final Message msg, final Actor a) {
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (msg.getSender() != null)
			Assert.isTrue(msg.getSender().equals(logged));
		else
			msg.setSender(logged);
		final Collection<Box> allBoxes = this.bService.findByOwner(logged.getId());
		Box out = this.bService.create();
		for (final Box b : allBoxes)
			if (b.getName().equals("OUT"))
				out = b;
		final Collection<Box> wout = msg.getBoxes();
		wout.add(out);
		msg.setBoxes(wout);
		msg.setReciever(a);
		msg.setSendTime(new Date());
		return this.msgRepository.save(msg);
	}

	public Collection<Message> findByBox(final Box b) {
		return this.msgRepository.findByBox(b.getId());
	}
}
