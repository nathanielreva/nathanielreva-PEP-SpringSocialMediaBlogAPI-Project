package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.AccountNotFoundException;
import com.example.exception.MessageExceedsLengthLimitException;
import com.example.exception.MessageIsBlankException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    public MessageService(
        MessageRepository messageRepository,
        AccountRepository accountRepository
    ){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addMessage(Message message){
        if(message.getMessageText() == null || message.getMessageText().isBlank()){
            throw new MessageIsBlankException();
        }
        if(message.getMessageText().length() >= 255){
            throw new MessageExceedsLengthLimitException();
        }
        //check user 
        if(!accountRepository.existsByAccountId(message.getPostedBy())){
            throw new AccountNotFoundException();
        };

        
        return messageRepository.save(message);
    }

    public List<Message> getAll(){
        return messageRepository.findAll();
    }

}
