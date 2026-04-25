package org.example.bookMyShow;

import lombok.Getter;

@Getter
public class Payment {
    PaymentStatus status;
    MovieBill movieBill;
    public Payment(MovieBill movieBill){
        status = PaymentStatus.PENDING;
        this.movieBill = movieBill;
    }
    public void markPaid(){
        this.status = PaymentStatus.PAID;
    }
}
