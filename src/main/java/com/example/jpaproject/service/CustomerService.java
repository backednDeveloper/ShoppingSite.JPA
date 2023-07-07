package com.example.jpaproject.service;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Customer;
import com.example.jpaproject.reporsitory.ConfirmCustomerProfilesRepository;
import com.example.jpaproject.reporsitory.ConfirmationTokenRepository;
import com.example.jpaproject.reporsitory.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final EmailService emailService;
    private final CustomerRepository customerRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmCustomerProfilesRepository confirmCustomerProfilesRepository;

    public List<Customer> allConfirmedCustomers(ModelAndView modelAndView, Customer customer) {
        List<Customer> allCustomer = confirmCustomerProfilesRepository.findAll();
        if(allCustomer.isEmpty()){
            modelAndView.addObject("Error" , "Have not any Customer");
            modelAndView.setViewName("Please Registration");
            log.atInfo().log("Have not available any Customer");
        }
        else {
            modelAndView.addObject("Customers",allCustomer);
            modelAndView.setViewName("All Customers");
            log.atInfo().log("Shows all not confirmed Customers");
        }
        return (List<Customer>) modelAndView;
    }
    public List<Customer> allNotConfirmedCustomers(ModelAndView modelAndView, Customer customer) {
        List<Customer> allCustomer = customerRepository.findAll();
        if(allCustomer.isEmpty()){
            modelAndView.addObject("Error" , "Have not any Customer");
            modelAndView.setViewName("Please Registration");
            log.atInfo().log("Have not available any Customer");
        }
        else {
            modelAndView.addObject("Customers",allCustomer);
            modelAndView.setViewName("All Customers");
            log.atInfo().log("Shows all confirmed Customers");
        }
        return allCustomer;
    }

    public ModelAndView customerRegistration(ModelAndView modelAndView, Customer customerEntity) {
        Optional<Customer> repository = customerRepository.findById(customerEntity.getId());
        if (repository.isEmpty()) {
            customerRepository.save(customerEntity);
            log.atInfo().log("Customers is saved to not confirmed Customer List");
            ConfirmationToken confirmationToken = new ConfirmationToken(customerEntity);
            log.atInfo().log("Confirmation token is created");
            confirmationTokenRepository.save(confirmationToken);
            log.atInfo().log("Confirmation token is saved to confirmationTokenRepository");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(customerEntity.getEmail());
            message.setSubject("Complete Registration!");
            message.setFrom("emilaze77@gmail.com");
            message.setText("To confirm your account please clikc here : " +
                    "https://localhost:8080/confirm-account?token="
                    + confirmationToken.getToken()
            );
            emailService.sendMail(message);
            modelAndView.addObject("email", customerEntity.getEmail());
            modelAndView.setViewName("Mail is sended");
            log.atInfo().log("Confirmation token is sended by email");
        } else {
            modelAndView.addObject("Error", customerEntity);
            modelAndView.setViewName("This account id is available");
            log.atInfo().log("Confirmation token is not sended");
        }
        return modelAndView;
    }

    public ModelAndView confirmCustomerAccount(ModelAndView modelAndView, Customer customerEntity, ConfirmationToken tokenEntity) {
        Optional<ConfirmationToken> token = confirmationTokenRepository.findById((int) tokenEntity.getId());
        log.atInfo().log("Confirmation token is searched");
        if (token.isPresent()) {
            Optional<Customer> customer = customerRepository.findById(customerEntity.getId());
            log.atInfo().log("Customer is searched");
            if (customer.isPresent()) {
                Customer newCustomer = customer.get();
                log.atInfo().log("Created new confirm Customer");
                confirmCustomerProfilesRepository.save(newCustomer);
                log.atInfo().log("Saved new confirm Customer");
                customerRepository.delete(customerEntity);
                log.atInfo().log("Customer deleted from not Confirmed Customers Lists");
                modelAndView.setViewName("Congratulation");
            } else {
                modelAndView.addObject("message", "This link is invalid");
                log.atInfo().log("Customer is not Present");
            }
        } else {
            modelAndView.setViewName("Registration is failed!!");
            log.atInfo().log("Confirmation token is Empty");
        }
        return modelAndView;
    }

    public ModelAndView deleteNotConfirmedCustomerAccount(ModelAndView modelAndView, Customer entity) {
        Optional<Customer> customer = customerRepository.findById(entity.getId());
        if (customer.isPresent()) {
            log.atInfo().log("Customer is searched for deleted");
            customerRepository.deleteById(entity.getId());
            modelAndView.addObject("Delete Account", "Your account has been deleted");
            modelAndView.setViewName("account-deleted");
            log.atInfo().log("Customer has been deleted");
        } else {
            modelAndView.addObject("Error", "Account is not available");
            modelAndView.setViewName("account-not-available");
            log.atInfo().log("Not Confirmed Customer is empty");
        }
        return modelAndView;
    }public ModelAndView deleteConfirmedCustomerAccount(ModelAndView modelAndView, Customer entity) {
        Optional<Customer> searchConfirmedCustomer = confirmCustomerProfilesRepository.findById(entity.getId());
        if (searchConfirmedCustomer.isPresent()) {
            log.atInfo().log("Customer is searched for deleted");
            confirmCustomerProfilesRepository.deleteById(entity.getId());
            modelAndView.addObject("Delete Account", "Your account has been deleted");
            modelAndView.setViewName("account-deleted");
            log.atInfo().log("Confirmed Customer has been deleted");
        } else {
            modelAndView.addObject("Error", "Account is not available");
            modelAndView.setViewName("account-not-available");
            log.atInfo().log("Confirmed Customer is empty");
        }
        return modelAndView;
    }

    public ModelAndView updateCustomerAccount(ModelAndView modelAndView, Customer entity) {
        Optional<Customer> optionalCustomer = customerRepository.findById(entity.getId());
        if (optionalCustomer.isPresent()) {
            log.atInfo().log("Customer is searched");
            Customer customer = optionalCustomer.get();
            customer.setName(entity.getName());
            customer.setPhone(entity.getPhone());
            customer.setEmail(entity.getEmail());
            customer.setAdress(entity.getAdress());

            customerRepository.save(customer);

            modelAndView.setViewName("Customer Update id Success");
            log.atInfo().log("Customer is saved");
        } else {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", "Customer not found");
            log.atInfo().log("Customer is empty");
        }

        return modelAndView;
    }

}
