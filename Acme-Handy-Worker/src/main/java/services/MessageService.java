
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
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//The repository that we are managing
	@Autowired
	private MessageRepository	msgRepository;

	//Auxiliary repositories
	@Autowired
	private ActorService		aService;
	@Autowired
	private BoxService			bService;

	@Autowired
	private SpamService			sService;

	@Autowired
	private SpamService			spamService;


	//CRUDs
	public Message create(final Actor sender) {
		final Message m = new Message();
		final Collection<Box> boxes = new ArrayList<>();
		final Date d = new Date();
		m.setSendTime(d);
		m.setBoxes(boxes);
		m.setReciever(sender);
		m.setSender(sender);
		return m;

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
		Assert.notNull(msg);
		final UserAccount ua = LoginService.getPrincipal();
		Message result = null;
		final Actor sender = this.aService.findByUserAccountId(ua.getId());
		if (msg.getId() != 0) {
			final Message ac = this.findOne(msg.getId());
			Assert.isTrue(msg.getSender().getAccount().equals(ua) || ac.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().getAccount().equals(ua) || ac.getReciever().getAccount().equals(ua));
			ac.setBoxes(msg.getBoxes());
			result = this.msgRepository.save(ac);
		} else {
			final Date lastTimeUpdated = new Date();
			this.spamService.isSpam(sender, msg.getBody());
			this.spamService.isSpam(sender, msg.getSubject());
			msg.setReciever(receiver);
			msg.setSender(sender);
			msg.setSendTime(lastTimeUpdated);
			final Collection<Box> boxes = new ArrayList<>();
			final Box in = this.bService.findByName(sender.getId(), "OUT");
			boxes.add(in);
			msg.setBoxes(boxes);
			result = this.msgRepository.save(msg);
			final Collection<Message> messages = in.getMessages();
			messages.add(result);
			in.setMessages(messages);
			this.bService.save(in);
		}
		return result;

	}
	public Message sends(final Message msg, final Actor receiver) {
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

		final Boolean spam = this.sService.isSpam(msg.getSender(), msg.getBody());
		Box send;
		if (spam)
			send = this.bService.findByName(sender.getId(), "SPAM");
		else
			send = this.bService.findByName(sender.getId(), "OUT");
		Collection<Message> outMsg = send.getMessages();
		if (outMsg == null)
			outMsg = new ArrayList<>();
		outMsg.add(saved);
		send.setMessages(outMsg);
		this.bService.save(send);

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
