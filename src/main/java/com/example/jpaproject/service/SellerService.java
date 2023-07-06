package com.example.jpaproject.service;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Seller;
import com.example.jpaproject.reporsitory.ConfirmationTokenRepository;
import com.example.jpaproject.reporsitory.ConfirmSellerRepository;
import com.example.jpaproject.reporsitory.SellerRepository;
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
public class SellerService {
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final SellerRepository sellerRepository;
    private final ConfirmSellerRepository sellerConfirmReposirtory;
    public List<Seller> allConfirmedSeller(ModelAndView modelAndView){
        List<Seller> allSellers = sellerConfirmReposirtory.findAll();
        if (allSellers.isEmpty()){
            modelAndView.addObject("Error" , "Does not find any seller");
            modelAndView.setViewName("Please regist first");
            log.atInfo().log("Shows all Confirmed Sellers");
        }
        else {
            modelAndView.addObject("Seller" , allSellers);
            modelAndView.setViewName("All Sellers");
            log.atInfo().log("Sellers list is empty");
        }
        return allSellers;
    }
    public List<Seller> allNotConfirmedSeller(ModelAndView modelAndView){
        List<Seller> allSellers = sellerRepository.findAll();
        if (allSellers.isEmpty()){
            modelAndView.addObject("Error" , "Does not find any seller");
            modelAndView.setViewName("Please regist first");
            log.atInfo().log("Shows all not confirmed sellers");
        }
        else {
            modelAndView.addObject("Seller" , allSellers);
            modelAndView.setViewName("All Sellers");
            log.atInfo().log("Not Confirmed Sellers is empty");
        }
        return allSellers;
    }
    public ModelAndView sellerRegistration(ModelAndView modelAndView, Seller seller, SimpleMailMessage message){
        Optional<Seller> sellerSearch = sellerRepository.findById(seller.getId());
        if(sellerSearch.isEmpty()){
            sellerRepository.save(seller);
            log.atInfo().log("Seller os saved");
            ConfirmationToken token = new ConfirmationToken(seller);
            log.atInfo().log("Token is created");
            confirmationTokenRepository.save(token);
            log.atInfo().log("Token is saved");
            message.setText("");
            message.setFrom("emilaze77@gmail.com");
            message.setText("Please click here : " +
                    "https://autohospital.com/confirm-accept?token="
                    + token.getToken() );
            emailService.sendMail(message);
            log.atInfo().log("Message is sended");
        }
        else {
            modelAndView.addObject("Error" , seller);
            modelAndView.setViewName("Please login!");
            log.atInfo().log("Seller not found");
        }
        return modelAndView;
    }
    public ModelAndView sellerAccountConfirm(ModelAndView modelAndView, ConfirmationToken token, Seller seller){
        Optional<ConfirmationToken> searcToken = confirmationTokenRepository.findById((int) token.getId());
        if(searcToken.isPresent()){
            log.atInfo().log("Search Token");
            Optional<Seller> searchSeller = sellerRepository.findById(seller.getId());
            if(searchSeller.isPresent()){
                log.atInfo().log("Search Seller for Confirm Token");
                Seller newSeller = searchSeller.get();
                sellerConfirmReposirtory.save(newSeller);
                modelAndView.addObject("Confirm" , newSeller);
                modelAndView.setViewName("Congratualiton seller is confirm");
                sellerRepository.delete(seller);
                log.atInfo().log("Seller added is Confirm Lists");
            }
            else {
                modelAndView.addObject("Error" , "Account not found");
                modelAndView.setViewName("Please try again");
                log.atInfo().log("Not fonirmed Seller is not found");
            }
        }else {
            modelAndView.addObject("Error" , "Token not found");
            modelAndView.setViewName("Please try again");
            log.atInfo().log("Token is not found");
        }
        return modelAndView;
    }
    public ModelAndView updateSeller (ModelAndView modelAndView, Seller seller){
        Optional<Seller> searchSeller = sellerRepository.findById(seller.getId());
        if(searchSeller.isPresent()){
            Seller updateSeller = new Seller();
            updateSeller.setId(seller.getId());
            updateSeller.setName(seller.getName());
            updateSeller.setAdress(seller.getAdress());
            updateSeller.setPhone(seller.getPhone());
            updateSeller.setEmail(seller.getEmail());
            sellerRepository.save(updateSeller);
            modelAndView.addObject("Account" , seller);
            modelAndView.setViewName("Account has been update");
            log.atInfo().log("Seller information is update");
        }
        else {
            modelAndView.addObject("Error", "This user not found");
            modelAndView.setViewName("Please first registration");
            log.atInfo().log("Seller not found");
        }
        return modelAndView;
    }
    public ModelAndView deleteNotConfirmedSeller(ModelAndView modelAndView, Seller seller){
        Optional<Seller> searchSeller = sellerRepository.findById(seller.getId());
        if(searchSeller.isPresent()){
            sellerRepository.delete(seller);
            modelAndView.addObject("Confirm" , "Your account has been deleted");
            modelAndView.setViewName("See you again");
            log.atInfo().log("Seller is deleted from not Confirmed lists");
        }
        else {
            modelAndView.addObject("Error" , "This account is not available");
            modelAndView.setViewName("Please try again");
            log.atInfo().log("Seller not found");
        }
        return modelAndView;
    } public ModelAndView deleteConfirmedSeller(ModelAndView modelAndView, Seller seller){
        Optional<Seller> searchConfirmSeller = sellerConfirmReposirtory.findById(seller.getId());
        if(searchConfirmSeller.isPresent()){
            sellerConfirmReposirtory.delete(seller);
            modelAndView.addObject("Confirm" , "Your account has been deleted");
            modelAndView.setViewName("See you again");
            log.atInfo().log("Seller is deleted from Confirmed lists");
        }
        else {
            modelAndView.addObject("Error" , "This account is not available");
            modelAndView.setViewName("Please try again");
            log.atInfo().log("Seller not found");
        }
        return modelAndView;
    }
}
