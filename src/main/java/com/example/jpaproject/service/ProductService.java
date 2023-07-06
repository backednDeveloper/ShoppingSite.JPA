package com.example.jpaproject.service;

import com.example.jpaproject.entity.Product;
import com.example.jpaproject.reporsitory.OrderRepository;
import com.example.jpaproject.reporsitory.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<Product> allProducts(ModelAndView modelAndView) {
        List<Product> allProducts1 = orderRepository.findAll();
        if (allProducts1.isEmpty()) {
            modelAndView.addObject("Error", "Does not find any product");
            modelAndView.setViewName("Please create new product");
        } else {
            modelAndView.addObject("Product" , allProducts1);
            modelAndView.setViewName("Product Lists");
        }
        return allProducts1;
    }

    public ModelAndView searchProducts(ModelAndView modelAndView, Product product) {
        Optional<Product> searchProduct = productRepository.findByIdAndCategory(product.getId(), product.getCategory());
        if (searchProduct.isPresent()) {
            modelAndView.addObject("Products", product);
            modelAndView.setViewName("Our products");
        } else {
            modelAndView.addObject("Error", "this product is not available");
            modelAndView.setViewName("Please try again");
        }
        return modelAndView;
    }

    public ModelAndView updateProducts(ModelAndView modelAndView, Product product) {
        Optional<Product> searchProduct = productRepository.findByIdAndCategory(product.getId(), product.getCategory());
        if (searchProduct.isPresent()) {
            Product newProdcut = new Product();
            newProdcut.setCategory(product.getCategory());
            newProdcut.setId(product.getId());
            newProdcut.setPrice(product.getPrice());
            newProdcut.setName(product.getName());
            productRepository.save(newProdcut);
            modelAndView.addObject("Update", newProdcut);
            modelAndView.setViewName("Product has been updated");
        } else {
            modelAndView.addObject("Product", product);
            modelAndView.setViewName("This product not available");
        }
        return modelAndView;
    }

    public ModelAndView deleteProducts(ModelAndView modelAndView, Product product) {
        Optional<Product> searchProduct = productRepository.findByIdAndCategory(product.getId(), product.getCategory());
        if (searchProduct.isPresent()) {
            productRepository.delete(product);
            modelAndView.addObject("Delete Account", "Your account has been deleted");
            modelAndView.setViewName("account-deleted");
        } else {
            modelAndView.addObject("Error", "Account is not available");
            modelAndView.setViewName("account-not-available");
        }
        return modelAndView;
    }

    public ModelAndView selectProductsForOrder(ModelAndView modelAndView, Product product) {
        Optional<Product> searchProduct = productRepository.findByIdAndCategory(product.getId(), product.getCategory());
        if (searchProduct.isPresent()) {
            Product selectProduct = new Product();
            selectProduct.setId(product.getId());
            selectProduct.setName(product.getName());
            selectProduct.setPrice(product.getPrice());
            selectProduct.setCategory(product.getCategory());
            List<Product> selectedProducts = new ArrayList<>();
            selectedProducts.add(selectProduct);
            modelAndView.addObject("Product", product);
            modelAndView.setViewName("Prodcut has been added");
        } else {
            modelAndView.addObject("Product", product);
            modelAndView.setViewName("This product not available");
        }
        return modelAndView;
    }

    public ModelAndView deletedProductsFromOrder(ModelAndView modelAndView, List<Product> selectedProducts) {
        for (Product product : selectedProducts) {
            orderRepository.delete(product);
            modelAndView.setViewName("Product is deleted");
        }
        return modelAndView;
    }

    public ModelAndView placeProductsToOrder(ModelAndView modelAndView, List<Product> selectedProducts) {
        for (Product product : selectedProducts) {
            orderRepository.save(product);
            modelAndView.addObject("Product", selectedProducts);
            modelAndView.setViewName("Products has been added");
        }
        return modelAndView;
    }

}
