package com.pgr.mvc.service;

import com.pgr.mvc.dto.MessageDTO;
import com.pgr.mvc.entity.MessageEntity;
import com.pgr.mvc.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageEntity sendMessage(String sender, MessageDTO dto) {
        MessageEntity message = new MessageEntity();
        message.setSender(sender);
        message.setReceiver(dto.getReceiver());
        message.setSubject(dto.getSubject());
        message.setContent(dto.getContent());
        return messageRepository.save(message);
    }

    public List<MessageEntity> getInbox(String username) {
        return messageRepository.findByReceiver(username);
    }

    public List<MessageEntity> getSentMessages(String username) {
        return messageRepository.findBySender(username);
    }
    public List<MessageEntity> getInboxMessages(String username) {
        return messageRepository.findByReceiverOrderBySentAtDesc(username);
    }

    public MessageEntity getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }
    public List<MessageEntity> getAllMessages() {
        return messageRepository.findAll();
    }
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
    public void deleteMessage(Long id, String username) {
        messageRepository.findById(id).ifPresent(msg -> {
            if (msg.getReceiver().equals(username)) {
                messageRepository.delete(msg);
            }
        });
    }
}
