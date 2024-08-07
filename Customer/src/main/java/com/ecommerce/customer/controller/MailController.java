package com.ecommerce.customer.controller;


import com.ecommerce.library.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/send-test")
    public String sendMailTest(Model model){
        model.addAttribute("title", "Sending mail");
        model.addAttribute("page", "Sending mail");
        mailService.sendMailTest();
        return "Success!";
    }
}
