package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class OrderService {


    @Autowired
    @Qualifier("creditCardPaymentService")
    private PaymentService paymentService;

    public void placeOrder(){
        if(paymentService.isPyamentDone()){
            System.out.println("Order placed Successully");
        }else{
            System.out.println("Order placing is failed due to Payment not completed... do payment....");
        }
    }

}
