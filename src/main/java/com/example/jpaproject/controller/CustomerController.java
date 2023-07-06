package com.example.jpaproject.controller;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Customer;
import com.example.jpaproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;
    @PostMapping("/register")
    public ModelAndView customerRegistration(ModelAndView modelAndView, Customer customerEntity, SimpleMailMessage message){
        return service.customerRegistration(modelAndView,customerEntity,message);
    }
    @PostMapping("/confirm-account")
    public ModelAndView confirmCustomerAccount(ModelAndView modelAndView, Customer customerEntity, ConfirmationToken tokenEntity){
        return service.confirmCustomerAccount(modelAndView, customerEntity, tokenEntity);
    }
    @DeleteMapping("delete")
    public ModelAndView deleteConfirmedCustomerAccount(ModelAndView modelAndView, Customer entity){
        return service.deleteConfirmedCustomerAccount(modelAndView, entity);
    }
    @PutMapping("/update")
    public ModelAndView updateCustomerAccount(ModelAndView modelAndView, Customer entity){
        return service.updateCustomerAccount(modelAndView, entity);
    }

}
