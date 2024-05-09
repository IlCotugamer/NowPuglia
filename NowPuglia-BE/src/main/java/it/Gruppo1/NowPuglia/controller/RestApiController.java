package it.Gruppo1.NowPuglia.controller;

import it.Gruppo1.NowPuglia.dto.JsonResponse.JsonDataDto;
import it.Gruppo1.NowPuglia.service.IJsonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {
    private final IJsonResponseService iJsonResponseService;

    @Autowired
    public RestApiController(IJsonResponseService iJsonResponseService) {
        this.iJsonResponseService = iJsonResponseService;
    }

    @GetMapping("/areIAuth")
    private String areIAuth() {
        return "Yes, you are authenticated!";
    }

    @RequestMapping(value = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    private JsonDataDto data() {
        return iJsonResponseService.runResponse();
    }
}
