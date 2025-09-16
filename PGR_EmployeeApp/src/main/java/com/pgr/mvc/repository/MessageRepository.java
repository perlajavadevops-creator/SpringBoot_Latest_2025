package com.pgr.mvc.repository;

import com.pgr.mvc.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByReceiver(String receiver);
    List<MessageEntity> findBySender(String sender);
    List<MessageEntity> findByReceiverOrderBySentAtDesc(String receiver);

}
