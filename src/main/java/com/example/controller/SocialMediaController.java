package com.example.controller;


import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountNotFoundException;
import com.example.exception.UsernameAlreadyRegisteredException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.lang.Throwable;
import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;
    public SocialMediaController(
        AccountService accountService,
        MessageService messageService
    ){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){

        try{
            Account newAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(newAccount);
        }catch(UsernameAlreadyRegisteredException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch(Throwable e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        try{
            Account loginAccount = accountService.loginAccount(account);
            return ResponseEntity.ok(loginAccount);
        }catch(AccountNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        
        try{
            Message newMessage = messageService.addMessage(message);
            return ResponseEntity.ok(newMessage);
        }catch(Throwable e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAll());
    }
}
