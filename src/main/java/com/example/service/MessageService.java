package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.AccountNotFoundException;
import com.example.exception.MessageExceedsLengthLimitException;
import com.example.exception.MessageIsBlankException;
import com.example.exception.MessageNotFoundException;
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

    public Optional<Message> getById(int id){

        return messageRepository.findById(id);
    }

    public int delete(int id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }else{
            throw new MessageNotFoundException();
        }
    }

    public Message update(int id, Message message){


        if(message.getMessageText() == null || message.getMessageText().isBlank()){
            throw new MessageIsBlankException();
        }
        if(message.getMessageText().length() >= 255){
            throw new MessageExceedsLengthLimitException();
        }

        //update
        Optional<Message> optionalmsg = this.getById(id);

        if(optionalmsg.isPresent()){
            Message messageDB = optionalmsg.get();
            messageDB.setMessageText(message.getMessageText());
            messageRepository.save(messageDB);

            return messageDB;
        }else{
            throw new MessageNotFoundException();
        }
    
    }

    public List<Message> getByAccount(int id){

        return messageRepository.findAllByPostedBy(id);
    }
}
