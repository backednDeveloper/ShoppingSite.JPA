package com.example.jpaproject.service;

import com.example.jpaproject.entity.CustomerEntity;
import com.example.jpaproject.reporsitory.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    public ModelAndView registration(ModelAndView modelAndView, CustomerEntity customerEntity, SimpleMailMessage simpleMailMessage){
        Optional<CustomerEntity> customerEntity1 = customerRepository.findById(customerEntity.getId());
        if(customerEntity1 != null){
            modelAndView.addObject("Error", "Account has not occured");
            modelAndView.setViewName("This profile alredy has available");
        }
        else {

            customerRepository.save(customerEntity);
            modelAndView.addObject("Congratulations", customerEntity.getId());
            modelAndView.setViewName("Account has been occured");
        }
    }
}
