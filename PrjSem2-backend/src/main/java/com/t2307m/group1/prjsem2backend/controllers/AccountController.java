package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//báo cho spring biết đây là controller
@RequestMapping("/api/v1/accounts")// connect to api = this link
public class AccountController {
    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerCustomer(@RequestBody Account account){
        try {
            Account newAccount = accountService.registerCustomer(account);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create account successfully!", newAccount)
            );
        }catch (Exception e){
            System.err.println("Err: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "create account not success!", "")
            );
        }
    }

    @PostMapping("/staff")
    public ResponseEntity<ResponseObject> createStaff(@RequestBody Account account){
        try {
            Account newStaff = accountService.createStaff(account);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create account successfully!", newStaff)
            );
        }catch (Exception e){
            System.err.println("Err: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "create account not success!", "")
            );
        }
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<ResponseObject> createAdmin(@RequestBody Account account){
        try{
            Account newAdmin = accountService.createAdmin(account);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create account successfully!", newAdmin)
            );
        }catch (Exception e){
            System.err.println("Err: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "create account not success!", "")
            );
        }
    }


    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllAccounts(Pageable pageable){
        Page<Account> accounts = accountService.getAllAccount(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
          new ResponseObject("ok", "Get all accounts successfully!", accounts)
        );
    }

}
