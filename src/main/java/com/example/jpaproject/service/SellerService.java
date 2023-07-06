package com.example.jpaproject.service;

import com.example.jpaproject.entity.ConfirmationToken;
import com.example.jpaproject.entity.Seller;
import com.example.jpaproject.reporsitory.ConfirmationTokenRepository;
import com.example.jpaproject.reporsitory.ConfirmSellerRepository;
import com.example.jpaproject.reporsitory.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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
        }
        else {
            modelAndView.addObject("Seller" , allSellers);
            modelAndView.setViewName("All Sellers");
        }
        return allSellers;
    }
    public List<Seller> allNotConfirmedSeller(ModelAndView modelAndView){
        List<Seller> allSellers = sellerRepository.findAll();
        if (allSellers.isEmpty()){
            modelAndView.addObject("Error" , "Does not find any seller");
            modelAndView.setViewName("Please regist first");
        }
        else {
            modelAndView.addObject("Seller" , allSellers);
            modelAndView.setViewName("All Sellers");
        }
        return allSellers;
    }
    public ModelAndView sellerRegistration(ModelAndView modelAndView, Seller seller, SimpleMailMessage message){
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
    public ModelAndView sellerAccountConfirm(ModelAndView modelAndView, ConfirmationToken token, Seller seller){
        Optional<ConfirmationToken> searcToken = confirmationTokenRepository.findById((int) token.getId());
        if(searcToken.isPresent()){
            Optional<Seller> searchSeller = sellerRepository.findById(seller.getId());
            if(searchSeller.isPresent()){
                Seller newSeller = searchSeller.get();
                sellerConfirmReposirtory.save(newSeller);
                modelAndView.addObject("Confirm" , newSeller);
                modelAndView.setViewName("Congratualiton seller is confirm");
            }
            else {
                modelAndView.addObject("Error" , "Account not found");
                modelAndView.setViewName("Please try again");
            }
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
        }
        else {
            modelAndView.addObject("Error", "This user not found");
            modelAndView.setViewName("Please first registration");
        }
        return modelAndView;
    }
    public ModelAndView deleteSeller(ModelAndView modelAndView, Seller seller){
        Optional<Seller> searchSeller = sellerRepository.findById(seller.getId());
        if(searchSeller.isPresent()){
            sellerRepository.delete(seller);
            modelAndView.addObject("Confirm" , "Your account has been deleted");
            modelAndView.setViewName("See you again");
        }
        else {
            modelAndView.addObject("Error" , "This account is not available");
            modelAndView.setViewName("Please try again");
        }
        return modelAndView;
    }
}
