package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderService {


   // @Autowired
    private PaymentService paymentService;

    public PaymentService getPaymentService() {
        return paymentService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    public OrderService(){

    }
    public OrderService(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    public void placeOrder(){
        if(paymentService.isPyamentDone()){
            System.out.println("Order placed Successully");
        }else{
            System.out.println("Order placing is failed due to Payment not completed... do payment....");
        }
    }

}
