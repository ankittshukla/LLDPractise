package org.example.bookMyShow;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Payment {
    private final UUID paymentId;
    private final double amount;
    private final MovieBill movieBill;
    private PaymentStatus status;

    public Payment(MovieBill movieBill){
        this.paymentId = UUID.randomUUID();
        this.amount = movieBill.getTotalPrice();
        this.status = PaymentStatus.PENDING;
        this.movieBill = movieBill;
    }

    public void markPaid(){
        this.status = PaymentStatus.PAID;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public void markRefunded() {
        this.status = PaymentStatus.REFUNDED;
    }
}
