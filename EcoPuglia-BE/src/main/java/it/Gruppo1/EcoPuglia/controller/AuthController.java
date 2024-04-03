package it.Gruppo1.EcoPuglia.controller;

import it.Gruppo1.EcoPuglia.model.UtentiModel;
import it.Gruppo1.EcoPuglia.service.IUsersManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final IUsersManagerService iUsersManagerService;

    @Autowired
    public AuthController(IUsersManagerService iUsersManagerService) {
        this.iUsersManagerService = iUsersManagerService;
    }

    @PostMapping("/register")
    public String aggiungiUtente(@RequestBody UtentiModel utentiModel) {
        return "test";
    }

}
