package org.example;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class Main {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        OrderService orderService = (OrderService) context.getBean("orderService");
        System.out.println("orderService "+orderService.hashCode());
        OrderService orderService1 = (OrderService) context.getBean("orderService");
        System.out.println("orderService1 "+orderService1.hashCode());
        orderService.placeOrder();
    }
}