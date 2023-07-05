package com.example.jpaproject.service;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Customer;
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

    public ModelAndView customerDisplayRegistration(ModelAndView modelAndView, Customer customerEntity) {
        modelAndView.addObject("Customer Registration", customerEntity);
        modelAndView.setViewName("Register");
        return modelAndView;
    }

    public ModelAndView customerRegistration(ModelAndView modelAndView, Customer customerEntity, SimpleMailMessage message) {
        Optional<Customer> repository = customerRepository.findById(customerEntity.getId());
        if (repository.isEmpty()) {
            customerRepository.save(customerEntity);
            ConfirmationToken confirmationToken = new ConfirmationToken(customerEntity);
            confirmationTokenRepository.save(confirmationToken);
            message.setTo(customerEntity.getEmail());
            message.setSubject("Complete Registration!");
            message.setFrom("emilaze77@gmail.com");
            message.setText("To confirm your account please clikc here : " +
                    "https://localhost:8080/confirm-account?token="
                    + confirmationToken.getToken()
            );
            emailService.sendMail(message);
            modelAndView.addObject("email", customerEntity.getEmail());
            modelAndView.setViewName("succesfully");
        } else {
            modelAndView.addObject("Error", customerEntity);
            modelAndView.setViewName("This account id is available");
        }
        return modelAndView;
    }

    public ModelAndView confirmCustomerAccount(ModelAndView modelAndView, Customer customerEntity, ConfirmationToken tokenEntity) {
        Optional<ConfirmationToken> token = confirmationTokenRepository.findById((int) tokenEntity.getId());
        if (token.isPresent()) {
            Optional<Customer> customer = customerRepository.findById(customerEntity.getId());
            if (customer.isPresent()) {
                Customer entity = customer.get();
                customerRepository.save(entity);
                modelAndView.setViewName("Congratulation");
            } else {
                modelAndView.addObject("message", "This link is invalid");
            }
        } else {
            modelAndView.setViewName("Registration is failed!!");
        }
        return modelAndView;
    }

    public ModelAndView deleteCustomerAccount(ModelAndView modelAndView, Customer entity) {
        Optional<Customer> customer = customerRepository.findById(entity.getId());
        if (customer.isPresent()) {
            customerRepository.deleteById(entity.getId());
            modelAndView.addObject("Delete Account", "Your account has been deleted");
            modelAndView.setViewName("account-deleted");
        } else {
            modelAndView.addObject("Error", "Account is not available");
            modelAndView.setViewName("account-not-available");
        }
        return modelAndView;
    }

    public ModelAndView updateCustomerAccount(ModelAndView modelAndView, Customer entity) {
        Optional<Customer> optionalCustomer = customerRepository.findById(entity.getId());

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setRegistration_date(entity.getRegistration_date());
            customer.setStatus(entity.getStatus());
            customer.setName(entity.getName());
            customer.setPhone(entity.getPhone());
            customer.setEmail(entity.getEmail());
            customer.setAdress(entity.getAdress());

            customerRepository.save(customer);

            modelAndView.setViewName("Customer Update id Success");
        } else {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", "Customer not found");
        }

        return modelAndView;
    }

}
