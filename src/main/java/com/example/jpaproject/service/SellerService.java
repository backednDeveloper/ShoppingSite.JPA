package com.example.jpaproject.service;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Seller;
import com.example.jpaproject.reporsitory.ConfirmationTokenRepository;
import com.example.jpaproject.reporsitory.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final SellerRepository sellerRepository;
    public ModelAndView createSellerConfirmation(ModelAndView modelAndView, Seller seller, SimpleMailMessage message){
        Optional<Seller> sellerSearch = sellerRepository.findById(seller.getId());
        if(sellerSearch.isEmpty()){
            sellerRepository.save(seller);
            ConfirmationToken token = new ConfirmationToken(seller);
            confirmationTokenRepository.save(token);
            message.setText("");
            message.setFrom("emilaze77@gmail.com");
            message.setText("Please click here : " +
                    "https://autohospital.com/confirm-accept?token="
                    + token.getToken() );
            emailService.sendMail(message);
        }
        else {
            modelAndView.addObject("Error" , seller);
            modelAndView.setViewName("Please login!");
        }
        return modelAndView;
    }
}
