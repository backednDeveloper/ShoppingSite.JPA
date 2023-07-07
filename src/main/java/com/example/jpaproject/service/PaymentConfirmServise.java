package com.example.jpaproject.service;

import com.example.jpaproject.entity.*;
import com.example.jpaproject.reporsitory.ConfirmationTokenRepository;
import com.example.jpaproject.reporsitory.OrderRepository;
import com.example.jpaproject.reporsitory.PaymentConfirmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConfirmServise {
    private final EmailService emailService;
    private final ConfirmationTokenRepository token;
    private final PaymentConfirmRepository paymentConfirmRepository;
    private final OrderRepository orderRepository;

    public List<PaymentConfirm> allPayments(ModelAndView modelAndView, PaymentConfirm payment) {
        List<PaymentConfirm> allPayment = paymentConfirmRepository.findAll();
        if (allPayment.isEmpty()) {
            modelAndView.addObject("Error", "Payment is not available");
            modelAndView.setViewName("Please try again");
        } else {
            modelAndView.addObject("Payment", payment);
            modelAndView.setViewName("Payment information");
        }
        return (List<PaymentConfirm>) modelAndView;
    }

    public ModelAndView orderPayment(ModelAndView modelAndView, Order order, Customer customer) {
        Optional<Order> searchOrder = orderRepository.findById((int) order.getId());
        if (searchOrder.isPresent()) {
            ConfirmationToken token = new ConfirmationToken(customer);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setText("Payment confirmation");
            message.setFrom("emilaze77@gmail.com");
            message.setSubject("Confirm for your payment click here :" +
                    " https://autohospitalaz.com/confirm-payment?token= "
                    + token.getToken());
            emailService.sendMail(message);
            modelAndView.addObject("Payment", customer);
            modelAndView.setViewName("Payment token is sended");
            log.atInfo().log("Payment token is sended");
        } else {
            modelAndView.addObject("Error", "Order is not found");
            modelAndView.setViewName("Please try again");
            log.atInfo().log("Order is not founded");
        }
        return modelAndView;
    }

    public ModelAndView confirmOrderToPayment(ModelAndView modelAndView, PaymentConfirm payment, ConfirmationToken confirmationToken) {
        Optional<ConfirmationToken> searchToken = token.findById((int) confirmationToken.getId());
        if (searchToken.isPresent()) {
            Optional<PaymentConfirm> serachPayment = paymentConfirmRepository.findById(payment.getId());
            if (serachPayment.isPresent()) {
                PaymentConfirm newPayment = new PaymentConfirm();
                newPayment.setId(payment.getId());
                newPayment.setDate(new Date());
                newPayment.setOrderEntity(new Order());
                newPayment.setConfirmEntity(new Confirm());
                paymentConfirmRepository.save(newPayment);
                modelAndView.addObject("Payment" , newPayment);
                modelAndView.setViewName("Payment is confirmed");
                log.atInfo().log("Succesfully");
            }
            else {
                modelAndView.addObject("Payment" , "Is not found");
                modelAndView.setViewName("Payment is not Confirmed");
                log.atInfo().log("Payment is not found");
            }
        } else {
            modelAndView.addObject("Token" , "Is not found");
            modelAndView.setViewName("Payment is not Confirmed");
            log.atInfo().log("Token is not found");
        }
        return modelAndView;
    }
}
