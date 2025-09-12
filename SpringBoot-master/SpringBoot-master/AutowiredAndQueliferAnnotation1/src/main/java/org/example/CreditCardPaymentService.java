package org.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("creditCardPaymentService")
public class CreditCardPaymentService implements PaymentService{
    @Override
    public boolean isPyamentDone() {
        return true;
    }
}
