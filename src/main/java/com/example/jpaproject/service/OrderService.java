package com.example.jpaproject.service;

import com.example.jpaproject.entity.Product;
import com.example.jpaproject.reporsitory.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmailService email;

    public List<Product> allOrders( ModelAndView modelAndView){
        List<Product> searchOrder = orderRepository.findAll();
        if (searchOrder.isEmpty()){
            modelAndView.addObject("Error", "Does not find any order");
            modelAndView.setViewName("Please create new order");
        }else {
            modelAndView.addObject("Order" , searchOrder);
            modelAndView.setViewName("All Orders");
        }
        return searchOrder;
    }


}
