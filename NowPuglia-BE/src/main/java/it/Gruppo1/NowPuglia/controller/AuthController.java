package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.model.UtentiModel;
import it.Gruppo1.NowPuglia.service.IUsersManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final IUsersManagerService iUsersManagerService;

    @Autowired
    public AuthController(IUsersManagerService iUsersManagerService) {
        this.iUsersManagerService = iUsersManagerService;
    }

    @PostMapping("/register")
    public String register(@RequestBody @Validated UtentiModel utentiModel) throws MethodArgumentNotValidException {
        return "test";
    }

    @PostMapping("/login")
    public String login(@RequestBody UtentiModel utentiModel) {
        return "test";
    }

    @PostMapping("/passwdreset")
    public String changePassword(@RequestBody @Validated UtentiModel utentiModel) {
        return "test";
    }
}
