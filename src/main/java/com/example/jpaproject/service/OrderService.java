package com.example.jpaproject.service;

import com.example.jpaproject.entity.Order;
import com.example.jpaproject.entity.Product;
import com.example.jpaproject.reporsitory.OrderRepository;
import com.example.jpaproject.reporsitory.SelectedProductsOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final SelectedProductsOrderRepository ProductRepository;
    private final OrderRepository orderRepository;

    public List<Order> allOrders(ModelAndView modelAndView) {
        List<Order> searchOrder = orderRepository.findAll();
        if (searchOrder.isEmpty()) {
            modelAndView.addObject("Error", "Does not find any order");
            modelAndView.setViewName("Please create new order");
            log.atInfo().log("Order list is empyte");
        } else {
            modelAndView.addObject("Order", searchOrder);
            modelAndView.setViewName("All Orders");
            log.atInfo().log("Shows all orders");
        }
        return searchOrder;
    }

    public ModelAndView addProductsToOrderList(ModelAndView modelAndView, Product product) {
        Optional<Product> sendRequest = ProductRepository.findById(product.getId());
        log.atInfo().log("Send request for added products to order");
        if (sendRequest.isPresent()) {
            Order order = new Order();
            order.setDate(new Date());
            order.setAdress(order.getAdress()); //???
            order.setWeight(Integer.parseInt(product.getWeight()));
            order.setId(product.getId());
            order.setPrice(product.getPrice());
            orderRepository.save(order);
            modelAndView.addObject("Order", order);
            modelAndView.setViewName("Order Information");
            log.atInfo().log("Added products to Order");
        } else {
            modelAndView.addObject("Error", "This order not confirmed");
            modelAndView.setViewName("Please try again");
            log.atInfo().log("Order not found");
        }
        return modelAndView;
    }
//    public ModelAndView deletedProductFromOrderLists(ModelAndView modelAndView, Product product){
//        Optional<Product> sendRequest = ProductRepository.findById(product.getId());
//        log.atInfo().log("Send request for added products to order");
//        if (sendRequest.isPresent()) {
//
//        }
//    }

}
