package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.PasswordIsInvalidException;
import com.example.exception.UsernameAlreadyRegisteredException;
import com.example.exception.UsernameIsBlankException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account){

        if(account.getUsername() == null || account.getUsername().isBlank()){
            throw new UsernameIsBlankException();
        }

        if(account.getPassword() == null || account.getPassword().isBlank() || account.getPassword().length() < 4){
            throw new PasswordIsInvalidException();
        }

        if(accountRepository.existsByUsername(account.getUsername())){
            throw new UsernameAlreadyRegisteredException();
        }

        
        return accountRepository.save(account);
    }
}
