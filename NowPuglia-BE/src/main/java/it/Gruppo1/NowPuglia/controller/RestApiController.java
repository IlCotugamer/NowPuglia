package it.Gruppo1.NowPuglia.controller;

/*  Rest Api controller
TODO:
    - Lettura db
    - Richieste al db
    - Query specifiche
*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    @GetMapping("/ciao")
    private String hello() {
        return "Hello World";
    }
}
