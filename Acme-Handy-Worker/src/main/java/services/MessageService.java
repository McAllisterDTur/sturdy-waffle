
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
		m.setTags(tags);
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

	public void delete(final Message msg, final Box box) {
		Assert.notNull(msg);
		Collection<Box> boxes;
		Collection<Message> messages;
		final UserAccount ua = LoginService.getPrincipal();
		final Actor act = this.aService.findByUserAccountId(ua.getId());
		final Message message = this.findOne(msg.getId());
		final Box trashBox = this.bService.findByName(act.getId(), "TRASH");
		final Box boxdelete = this.bService.findOne(box.getId());
		Assert.isTrue(msg.getSender().getAccount().equals(ua) || message.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().getAccount().equals(ua) || message.getReciever().getAccount().equals(ua));
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

	public void deleteMessages(final Message msg) {
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
			Box out;
			final Date lastTimeUpdated = new Date();
			if (this.spamService.isSpam(sender, msg.getBody()) || this.spamService.isSpam(sender, msg.getSubject())) {
				out = null;
				out = this.bService.findByName(receiver.getId(), "SPAM");
			} else
				out = this.bService.findByName(receiver.getId(), "IN");
			//Modificamos el mensaje
			msg.setReciever(receiver);
			msg.setSender(sender);
			msg.setSendTime(lastTimeUpdated);
			final Collection<Box> boxes = new ArrayList<>();
			final Box in = this.bService.findByName(sender.getId(), "OUT");
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
	//	public Message copy(final Message msg) {
	//		//Comprobaciones de seguridad
	//		Assert.notNull(msg);
	//		final UserAccount ua = LoginService.getPrincipal();
	//		Message result = null;
	//		final Message ac = this.findOne(msg.getId());
	//		final Collection<Box> originalBoxes = ac.getBoxes();
	//		Assert.isTrue(msg.getSender().getAccount().equals(ua) || ac.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().getAccount().equals(ua) || ac.getReciever().getAccount().equals(ua));
	//		final Actor actor = this.aService.findByUserAccountId(ua.getId());
	//		//Comprobamos que el box agregado pertenezca al usuario
	//		final Collection<Box> autoBox = this.bService.findByOwner(actor.getId());
	//		final Collection<Box> newBox = msg.getBoxes();
	//		newBox.removeAll(originalBoxes);
	//		Assert.isTrue(autoBox.contains(newBox.iterator().next()));
	//		ac.getBoxes().addAll(msg.getBoxes());
	//		final Collection<Box> boxes = msg.getBoxes();
	//		boxes.add(newBox.iterator().next());
	//		msg.setBoxes(boxes);
	//		result = this.msgRepository.save(msg);
	//		final Collection<Message> messages = newBox.iterator().next().getMessages();
	//		messages.add(msg);
	//		newBox.iterator().next().setMessages(messages);
	//		this.bService.save(newBox.iterator().next());
	//		return result;
	//	}

	public Message copy(final Message msg) {
		System.out.println("llega al copy service");
		Assert.notNull(msg, "Message copy null");
		final UserAccount ua = LoginService.getPrincipal();
		Message result = null;
		final Message ac = this.findOne(msg.getId());
		Assert
			.isTrue((msg.getSender().getAccount().equals(ua) || ac.getSender().getAccount().equals(msg.getSender().getAccount()) || msg.getReciever().getAccount().equals(ua) || ac.getReciever().getAccount().equals(ua)), "Message not belong to the actor");
		final Actor actor = this.aService.findByUserAccountId(ua.getId());
		//Comprobamos que el box agregado pertenezca al usuario
		final Collection<Box> autoBox = this.bService.findByOwner(actor.getId());
		Collection<Box> newBox = msg.getBoxes();
		final Set<Box> unique = new HashSet<>(newBox);
		newBox = unique;
		final Collection<Box> compr = ac.getBoxes();
		compr.removeAll(newBox);
		Assert.isTrue(autoBox.containsAll(compr), "New box not belong to the loged actor");
		ac.setBoxes(newBox);
		result = this.msgRepository.save(ac);
		//Editamos los buzones
		return result;
	}
	public Collection<Message> findByBox(final Box b) {
		try {
			return this.msgRepository.findByBox(b.getId());
		} catch (final NullPointerException e) {
			return null;
		}

	}

	public void broadcastMessage(final Message msg) {
		Assert.isTrue(AuthenticationUtility.checkAuthority("ADMIN"));
		for (final Actor a : this.aService.findAll())
			if (!this.aService.findByUserAccountId(LoginService.getPrincipal().getId()).equals(a))
				this.send(msg, a);
	}
}
