package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("contato")
public class ContatoController {
    @Autowired
    IEmailService emailService;

    @PostMapping
    public void enviaEmail(@RequestBody Map<String, String> body) {
        emailService.sendEmailToClient(body.get("subject"), body.get("content"));
    }
}
