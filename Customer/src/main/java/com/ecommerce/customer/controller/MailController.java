/*package com.ecommerce.customer.controller;


import com.ecommerce.library.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@RestController: This annotation is a specialized version of @Controller, typically used for RESTful web services. It combines @Controller and @ResponseBody, meaning that every method in the class will return data directly as a response body, usually in JSON format.
//@RequestMapping("/mail"): This annotation at the class level defines a base URL path (/mail) for all the request mappings within this controller.
//@RequiredArgsConstructor: This Lombok annotation automatically generates a constructor with required arguments for all final fields, in this case, mailService.
//MailService: A service class (assumed to be defined elsewhere in the application) responsible for sending emails.
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    //@GetMapping("/send-test"): Maps HTTP GET requests to the /mail/send-test URL to the sendMailTest method.
    //Email Sending Logic: The sendMailTest() method calls the sendMailTest() method from the MailService class to send a test email.
    //Response: If the mail-sending operation is successful, the method returns the string "Success" as the response.
    @GetMapping("/send-test")
    public String sendMailTest(){
        mailService.sendMailTest();
        return "Success";
    }
}
*/
