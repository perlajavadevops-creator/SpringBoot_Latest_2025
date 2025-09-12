package com.pgr.spring.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = "com.pgr.spring.beans")
public class AnnotationsMain {

    //private Customer customer;
    @Bean

    public CustomerService customerService(){
        return new CustomerService();
    }

    public static void main(String[] args) {
        AnnotationsMain annotationsMain = new AnnotationsMain();
        CustomerService cust = annotationsMain.customerService();
        System.out.println(cust.getCustomerDetails().getCustmerId());
        System.out.println(cust.getCustomerDetails().getOrder().getOrderId());

    }
}
