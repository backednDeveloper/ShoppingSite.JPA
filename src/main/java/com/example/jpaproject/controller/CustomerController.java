package com.example.jpaproject.controller;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Customer;
import com.example.jpaproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/regiter")
    public ModelAndView doLogin(ModelAndView modelAndView ,Customer customer) {
        return service.customerRegistration(modelAndView,customer);
    }

    @PostMapping("/register")
    public ModelAndView customerRegistration(ModelAndView modelAndView, Customer customerEntity) {
        return service.customerRegistration(modelAndView, customerEntity);
    }

    @PostMapping("/confirm-account?token=")
    public ModelAndView confirmCustomerAccount(ModelAndView modelAndView, Customer customerEntity, ConfirmationToken tokenEntity) {
        return service.confirmCustomerAccount(modelAndView, customerEntity, tokenEntity);
    }

    @DeleteMapping("delete")
    public ModelAndView deleteConfirmedCustomerAccount(ModelAndView modelAndView, Customer entity) {
        return service.deleteConfirmedCustomerAccount(modelAndView, entity);
    }

    @PutMapping("/update")
    public ModelAndView updateCustomerAccount(ModelAndView modelAndView, Customer entity) {
        return service.updateCustomerAccount(modelAndView, entity);
    }

}
