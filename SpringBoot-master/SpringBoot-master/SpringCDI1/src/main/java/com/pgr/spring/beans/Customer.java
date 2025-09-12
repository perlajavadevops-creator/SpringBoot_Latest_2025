package com.pgr.spring.beans;

import org.springframework.beans.factory.annotation.Autowired;

public class Customer {

    private Order order;
    private int custmerId;


    public int getCustmerId() {
        return custmerId;
    }

    public void setCustmerId(int custmerId) {
        this.custmerId = custmerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
