package com.example.jpaproject.service;

import com.example.jpaproject.entity.ConfirmationTokenEntity;
import com.example.jpaproject.entity.CustomerEntity;
import com.example.jpaproject.reporsitory.ConfirmationTokenRepository;
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
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ModelAndView customerDisplayRegistration(ModelAndView modelAndView, CustomerEntity customerEntity) {
        modelAndView.addObject("Customer Registration", customerEntity);
        modelAndView.setViewName("Register");
        return modelAndView;
    }

    public ModelAndView customerRegistration(ModelAndView modelAndView, CustomerEntity customerEntity, SimpleMailMessage message) {
        Optional<CustomerEntity> repository = customerRepository.findById(customerEntity.getId());
        if (repository.isEmpty()) {
            customerRepository.save(customerEntity);
            ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(customerEntity);
            confirmationTokenRepository.save(confirmationToken);
            message.setTo(customerEntity.getEmail());
            message.setSubject("Complete Registration!");
            message.setFrom("emilaze77@gmail.com");
            message.setText("To confirm your account please clikc here : " +
                    "https://localhost:8080/confirm-account?token="
                    + confirmationToken.getToken()
            );
            emailService.sendMail(message);
            modelAndView.addObject("email" , customerEntity.getEmail());
            modelAndView.setViewName("succesfully");
        } else {
            modelAndView.addObject("Error", customerEntity);
            modelAndView.setViewName("This account id is available");
        }
        return modelAndView;
    }
    public ModelAndView confrimAccountRegistration(ModelAndView modelAndView, CustomerEntity customerEntity, ConfirmationTokenEntity tokenEntity){
        Optional<ConfirmationTokenEntity> token = confirmationTokenRepository.findById((int) tokenEntity.getId());
        if(token.isPresent()){
            Optional<CustomerEntity> customer = customerRepository.findById(customerEntity.getId());
            if(customer.isPresent()){
                CustomerEntity entity = customer.get();
                customerRepository.save(entity);
                modelAndView.setViewName("Congratulation");
            }
            else {
                modelAndView.addObject("message","This link is invalid");
            }
        }else {
            modelAndView.setViewName("Registration is failed!!");
        }
        return modelAndView;
    }
    public ModelAndView deleteCustomerAccount(ModelAndView modelAndView, CustomerEntity customerEntity){
        Optional<CustomerEntity> customer = customerRepository.findById(customerEntity.getId());
        if(customer.isPresent()){
            customerRepository.deleteById(customerEntity.getId());
            modelAndView.addObject("Delete Account", "Your account has been deleted");
            modelAndView.setViewName("See you again");
        }
        else{
            modelAndView.addObject("Error", "Account is not available");
        }
        return modelAndView;
    }
}
