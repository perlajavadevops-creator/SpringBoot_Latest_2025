package com.pgr.spring.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

   /* @Autowired 
    Customer customer;*/
    public Customer getCustomerDetails(){
        Customer customer = new Customer();
        customer.setCustmerId(1001);
        Order order =  new Order();
        order.setOrderId(10001);
        customer.setOrder(order);
        return customer;
    }
}
