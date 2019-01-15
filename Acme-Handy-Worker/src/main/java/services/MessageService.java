
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
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
	private SpamService			spamService;


	//CRUDs
	public Message create(final Actor sender) {
		final Message m = new Message();
		final Collection<Box> boxes = new ArrayList<>();
		final Collection<String> tags = new ArrayList<>();
		final Collection<Actor> receiver = new ArrayList<>();
		final Date d = new Date();
		m.setTags(tags);
		m.setSendTime(d);
		m.setBoxes(boxes);
		m.setReciever(receiver);
		m.setSender(sender);
		m.setBody("");
		m.setPriority("");
		m.setSubject("");

		return m;

	}
	public Collection<Message> findAll() {
		final Collection<Message> all = this.msgRepository.findAll();
		final Collection<Message> res = new ArrayList<>();
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		for (final Message m : all)
			if (m.getSender().equals(logged) || m.getReciever().equals(logged) || AuthenticationUtility.checkAuthority("ADMIN"))
				res.add(m);

		return res;
	}

	public Message findOne(final int msgId) {
		final Message res = this.msgRepository.findOne(msgId);
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue((res.getSender().equals(logged) || res.getReciever().contains(logged)), "Message not belong to the logged actor");
		return res;
	}

	public void delete(final Message msg, final Box box) {
		Assert.notNull(msg, "Message can not be null");
		Collection<Box> boxes;
		Collection<Message> messages;
		final UserAccount ua = LoginService.getPrincipal();
		final Actor act = this.aService.findByUserAccountId(ua.getId());
		final Message message = this.findOne(msg.getId());
		final Box trashBox = this.checkSystemBox(this.bService.findByName(act.getId(), "TRASH"));
		final Box boxdelete = this.bService.findOne(box.getId());
		Assert.isTrue((msg.getSender().getAccount().equals(ua) || message.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().contains(ua) || message.getReciever().contains(ua)), "Message not belong to the logged actor");
		if (!(box.getName().equals("TRASH"))) {
			//Tratamos el mensaje
			boxes = message.getBoxes();
			boxes.remove(boxdelete);
			boxes.add(trashBox);
			message.setBoxes(boxes);
			this.msgRepository.save(message);
			//Tratamos los buzones
			messages = boxdelete.getMessages();
			messages.remove(message);
			boxdelete.setMessages(messages);
			this.bService.save(boxdelete);
			messages = trashBox.getMessages();
			messages.add(message);
			trashBox.setMessages(messages);
			this.bService.save(trashBox);
		} else {
			//Tratamos los mensajes y buzones
			boxes = message.getBoxes();
			final Collection<Box> boxesAct = this.bService.findByOwner(act.getId());
			boxes.removeAll(boxesAct);
			for (final Box b : boxesAct) {
				final Collection<Message> mensajes = b.getMessages();
				mensajes.remove(message);
				b.setMessages(mensajes);
				this.bService.save(b);
			}
			if (boxes.isEmpty())
				this.msgRepository.delete(message.getId());
			else {
				message.setBoxes(boxes);
				this.msgRepository.save(message);
			}
		}
	}

	//Other methods
	public void deleteMessages(final Message msg, final Box box) {
		Assert.notNull(msg);
		Collection<Box> boxes;
		Collection<Message> messages;
		final UserAccount ua = LoginService.getPrincipal();
		final Actor act = this.aService.findByUserAccountId(ua.getId());
		final Message message = this.findOne(msg.getId());
		final Box trashBox = this.checkSystemBox(this.bService.findByName(act.getId(), "TRASH"));
		final Box boxdelete = this.bService.findOne(box.getId());
		Assert.isTrue(msg.getSender().getAccount().equals(ua) || message.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().contains(ua) || message.getReciever().contains(ua));
		if (!(box.getName().equals("TRASH"))) {
			//Tratamos el mensaje
			boxes = message.getBoxes();
			boxes.remove(boxdelete);
			boxes.add(trashBox);
			message.setBoxes(boxes);
			this.msgRepository.save(message);
			//Tratamos los buzones
			messages = trashBox.getMessages();
			messages.add(message);
			trashBox.setMessages(messages);
			this.bService.save(trashBox);
		}
	}

	public Message send(final Message msg, final Actor receiver) {
		final UserAccount ua = LoginService.getPrincipal();
		Message result = null;
		final Actor sender = this.aService.findByUserAccountId(ua.getId());
		if (msg.getId() != 0) {
			final Message ac = this.findOne(msg.getId());
			Assert.isTrue(msg.getSender().getAccount().equals(ua) || ac.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().contains(ua) || ac.getReciever().contains(ua), "Message not belong to the actor");
			ac.setBoxes(msg.getBoxes());
			result = this.msgRepository.save(ac);
		} else {
			Box out;
			final Date lastTimeUpdated = new Date();
			if (this.spamService.isSpam(sender, msg.getBody()) || this.spamService.isSpam(sender, msg.getSubject()) || this.spamService.isSpam(sender, msg.getTags())) {
				out = null;
				out = this.checkSystemBox(this.bService.findByName(receiver.getId(), "SPAM"));
			} else
				out = this.checkSystemBox(this.bService.findByName(receiver.getId(), "IN"));
			//Modificamos el mensaje

			final Collection<Actor> receptor = new ArrayList<>();
			receptor.add(receiver);
			msg.setReciever(receptor);
			msg.setSender(sender);
			msg.setSendTime(lastTimeUpdated);
			final Collection<Box> boxes = new ArrayList<>();
			final Box in = this.checkSystemBox(this.bService.findByName(sender.getId(), "OUT"));
			boxes.add(in);
			boxes.add(out);
			msg.setBoxes(boxes);
			result = this.msgRepository.save(msg);

			//Modificamos el buzon out
			final Collection<Message> messagesIn = in.getMessages();
			messagesIn.add(result);
			in.setMessages(messagesIn);
			this.bService.save(in);

			//Modificamos el buzon in
			final Collection<Message> messagesOut = out.getMessages();
			messagesOut.add(result);
			out.setMessages(messagesOut);

			this.bService.save(out);

		}
		return result;

	}

	public Message copy(final Message msg) {
		Assert.notNull(msg, "Message copy null");
		final UserAccount ua = LoginService.getPrincipal();
		Message result = null;
		final Message ac = this.findOne(msg.getId());
		Assert.isTrue((msg.getSender().getAccount().equals(ua) || ac.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().contains(ua) || ac.getReciever().contains(ua)), "Message not belong to the actor");
		final Actor actor = this.aService.findByUserAccountId(ua.getId());
		//Comprobamos que el box agregado pertenezca al usuario
		final Collection<Box> autoBox = this.bService.findByOwner(actor.getId());
		Collection<Box> newBox = msg.getBoxes();
		final Set<Box> unique = new HashSet<>(newBox);
		newBox = unique;
		final Collection<Box> compr = ac.getBoxes();
		unique.removeAll(compr);
		Assert.isTrue(autoBox.containsAll(unique), "New box not belong to the loged actor");
		compr.addAll(newBox);
		//Editamos los buzones
		ac.setBoxes(compr);
		result = this.msgRepository.save(ac);
		return result;
	}

	public Collection<Message> findByBox(final Box b) {
		Collection<Message> res = new ArrayList<>();
		final Actor logged = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		try {
			if (b.getOwner().equals(logged))
				res = this.msgRepository.findByBox(b.getId());
		} catch (final NullPointerException e) {
			res = null;
		}
		return res;
	}

	public void broadcastMessage(final Message msg) {
		Assert.isTrue(AuthenticationUtility.checkAuthority("ADMIN"), "Logged actor is not an administrator");
		final Collection<Actor> receivers = this.aService.findAll();
		final UserAccount ua = LoginService.getPrincipal();
		final Actor actor = this.aService.findByUserAccountId(ua.getId());
		receivers.remove(actor);
		Assert.notNull(msg, "Message can not be null");
		Message result = null;
		final Actor sender = this.aService.findByUserAccountId(ua.getId());
		if (msg.getId() != 0) {
			final Message ac = this.findOne(msg.getId());
			Assert.isTrue(msg.getSender().getAccount().equals(ua) || ac.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().contains(ua) || ac.getReciever().contains(ua), "Message not belong to the actor");
			ac.setBoxes(msg.getBoxes());
			result = this.msgRepository.save(ac);
		} else {
			Box out;
			final Date lastTimeUpdated = new Date();

			//Modificamos el mensaje
			final Collection<Actor> receptor = new ArrayList<>();
			receptor.addAll(receivers);
			msg.setReciever(receptor);
			msg.setSender(sender);
			msg.setSendTime(lastTimeUpdated);

			final Collection<Box> boxes = new ArrayList<>();
			final Box in = this.checkSystemBox(this.bService.findByName(sender.getId(), "OUT"));
			boxes.add(in);
			for (final Actor a : receivers) {
				if (this.spamService.isSpam(sender, msg.getBody()) || this.spamService.isSpam(sender, msg.getSubject()) || this.spamService.isSpam(sender, msg.getTags())) {
					out = null;
					out = this.checkSystemBox(this.bService.findByName(a.getId(), "SPAM"));
				} else
					out = this.checkSystemBox(this.bService.findByName(a.getId(), "IN"));
				if (out != null)
					boxes.add(out);
			}
			msg.setBoxes(boxes);
			result = this.msgRepository.save(msg);
			//Modificamos el buzon

			for (final Box b : boxes) {
				final Collection<Message> messagesOut = b.getMessages();
				messagesOut.add(result);
				b.setMessages(messagesOut);
				this.bService.save(b);
			}

		}
	}

	public Box checkSystemBox(final Collection<Box> boxes) {
		Box box = null;
		for (final Box b : boxes)
			if (!(b.getDeleteable()))
				box = b;
		return box;
	}

}
