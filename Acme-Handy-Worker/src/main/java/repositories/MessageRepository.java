package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
