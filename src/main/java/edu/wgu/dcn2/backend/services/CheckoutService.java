package edu.wgu.dcn2.backend.services;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
