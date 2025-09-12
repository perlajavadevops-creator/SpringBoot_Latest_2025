package org.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("upiPaymentService")
public class UpiPaymentService implements PaymentService{
    @Override
    public boolean isPyamentDone() {
        return true;
    }
}
