/*package com.ecommerce.customer.controller;

import com.ecommerce.library.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/send-test")
    public String sendMailTest(){
        mailService.sendMailTest();
        return "Success!";
    }
}
*/